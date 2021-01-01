package me.illgilp.packmodemenu;

import java.util.List;
import java.util.Random;
import me.illgilp.packmodemenu.gui.ConfigScreen;
import me.illgilp.packmodemenu.lang.LanguageManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(
    modid = PackModeMenu.MOD_ID,
    name = PackModeMenu.MOD_NAME,
    version = PackModeMenu.VERSION
)
@Mod.EventBusSubscriber
public class PackModeMenu {

    public static final String MOD_ID = "packmodemenu";
    public static final String MOD_NAME = "PackModeMenu";
    public static final String VERSION = "1.0.2";

    @Mod.Instance(MOD_ID)
    public static PackModeMenu INSTANCE;

    private LanguageManager languageManager;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        languageManager = new LanguageManager();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

    }

    private static int optionsButtonId = 0;

    @SubscribeEvent
    public static void onInitGuiEvent(final GuiScreenEvent.InitGuiEvent event) {
        final GuiScreen gui = event.getGui();
        if (gui instanceof GuiOptions) {
            List<GuiButton> buttonList = event.getButtonList();
            boolean ok = true;
            do {
                optionsButtonId = new Random().nextInt(Integer.MAX_VALUE);
                for (final GuiButton button : buttonList) {
                    if (button.id == optionsButtonId) {
                        ok = false;
                        break;
                    }
                }
            } while (!ok);

            buttonList.add(new GuiButton(optionsButtonId, gui.width / 2 + 5, gui.height / 6  + 24 - 9, 150, 20, PackModeMenu.INSTANCE.getLanguageManager().getTranslation("packmodemenu.options.pack_mode")));
            event.setButtonList(buttonList);
        }
    }

    @SubscribeEvent
    public static void onActionGuiEvent(final GuiScreenEvent.ActionPerformedEvent event) {
        if (event.getGui() instanceof GuiOptions) {
            if (event.getButton().id == optionsButtonId) {
                Minecraft.getMinecraft().displayGuiScreen(new ConfigScreen(Minecraft.getMinecraft().currentScreen));
            }
        }
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }
}
