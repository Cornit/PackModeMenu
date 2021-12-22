package me.illgilp.packmodemenu.proxy;

import java.util.List;
import java.util.Random;
import me.illgilp.packmodemenu.gui.ConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
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

            buttonList.add(new GuiButton(optionsButtonId, gui.width / 2 + 5, gui.height / 6  + 24 - 9, 150, 20, I18n.format("packmodemenu.options.pack_mode")));
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
}
