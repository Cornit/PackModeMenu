package me.illgilp.packmodemenu;

import me.illgilp.packmodemenu.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
    modid = PackModeMenu.MOD_ID,
    name = PackModeMenu.MOD_NAME,
    version = PackModeMenu.VERSION,
    clientSideOnly = true
)
@Mod.EventBusSubscriber
public class PackModeMenu {

    public static final String MOD_ID = "packmodemenu";
    public static final String MOD_NAME = "PackModeMenu";
    public static final String VERSION = "1.0.6";

    @Mod.Instance(MOD_ID)
    public static PackModeMenu INSTANCE;

    @SidedProxy(clientSide = "me.illgilp.packmodemenu.proxy.ClientProxy")
    private static CommonProxy commonProxy;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        commonProxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        commonProxy.init(event);
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {
        commonProxy.postInit(event);
    }
}
