package mcjty.fxcontrol;


import mcjty.fxcontrol.cache.StructureCache;
import mcjty.fxcontrol.compat.LostCitySupport;
import mcjty.fxcontrol.proxy.CommonProxy;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.Logger;

@Mod(modid = FxControl.MODID, name = FxControl.MODNAME,
        dependencies =
                "after:forge@[" + FxControl.MIN_FORGE11_VER + ",)",
        version = FxControl.VERSION,
        acceptedMinecraftVersions = "[1.12,1.13)",
        acceptableRemoteVersions = "*")
public class FxControl {

    public static final String MODID = "fxcontrol";
    public static final String MODNAME = "FxControl";
    public static final String VERSION = "0.0.1";
    public static final String MIN_FORGE11_VER = "13.19.0.2176";

    @SidedProxy(clientSide = "mcjty.fxcontrol.proxy.ClientProxy", serverSide = "mcjty.fxcontrol.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static FxControl instance;

    public static Logger logger;

    public static boolean lostcities = false;
    public static boolean gamestages = false;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        logger = event.getModLog();
        proxy.preInit(event);

        lostcities = Loader.isModLoaded("lostcities");
        gamestages = Loader.isModLoaded("gamestages");

        if (lostcities) {
            LostCitySupport.register();
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new CmdReload());
        event.registerServerCommand(new CmdDebug());
    }

    @Mod.EventHandler
    public void serverStopped(FMLServerStoppedEvent event) {
        StructureCache.CACHE.clean();
    }
}