package me.illgilp.packmodemenu.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import me.illgilp.packmodemenu.PackModeMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.config.GuiButtonExt;

public final class ConfigScreen extends GuiScreen {

    private GuiButtonExt switchBtn;
    private GuiButtonExt saveBtn;
    private String mode = "";
    private String defaultMode = "";
    private Property acceptedModes;

    private Property packMode;

    private Configuration configuration;

    private GuiScreen lastScreen;

    private boolean firstInit = true;

    public ConfigScreen(GuiScreen lastScreen) {
        super();
        this.lastScreen = lastScreen;

        configuration = new Configuration(new File("config/packmode.cfg"));
        configuration.load();
        packMode = configuration.get("general", "packMode", "normal");
        acceptedModes = configuration.get("general", "acceptedModes", new String[] {"normal", "expert"});

    }

    @Override
    public void initGui() {
        if (firstInit) {
            mode = packMode.getString();
            defaultMode = packMode.getString();
        }
        switchBtn = new GuiButtonExt(0, width / 2 - 100, height / 6, getButtonText());
        buttonList.add(switchBtn);
        saveBtn = new GuiButtonExt(1, this.width / 2 - 155, this.height / 6 + 120 - 6, 150, 20, PackModeMenu.INSTANCE.getLanguageManager().getTranslation("packmodemenu.options.save"));
        saveBtn.enabled = !mode.equals(defaultMode);
        this.buttonList.add(saveBtn);
        this.buttonList.add(new GuiButtonExt(2, this.width / 2 + 5, this.height / 6 + 120 - 6, 150, 20, PackModeMenu.INSTANCE.getLanguageManager().getTranslation("packmodemenu.options.cancel")));


        super.initGui();
        firstInit = false;
    }

    private String getButtonText() {
        return PackModeMenu.INSTANCE.getLanguageManager().getTranslation("packmodemenu.options.pack_mode") + ": " + (!mode.equals(defaultMode) ? "§a" : "") + mode.toUpperCase() + (!mode.equals(defaultMode) ? "" : " (" + PackModeMenu.INSTANCE.getLanguageManager().getTranslation("packmodemenu.options.current") + ")");
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, PackModeMenu.INSTANCE.getLanguageManager().getTranslation("packmodemenu.options.pack_mode"), this.width / 2, 15, 16777215);

        if (!mode.equals(defaultMode)) {
            this.drawCenteredString(this.fontRenderer, PackModeMenu.INSTANCE.getLanguageManager().getTranslation("packmodemenu.options.warning.restart.line1"), this.width / 2, this.height / 6 + 75 - 6, 0xFF5555);
            this.drawCenteredString(this.fontRenderer, PackModeMenu.INSTANCE.getLanguageManager().getTranslation("packmodemenu.options.warning.restart.line2"), this.width / 2, this.height / 6 + 75 - 6 + 10, 0xFF5555);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == switchBtn.id) {
            mode = acceptedModes.getStringList()[(new ArrayList<>(Arrays.asList(acceptedModes.getStringList())).lastIndexOf(mode) + 1) % acceptedModes.getStringList().length];
            switchBtn.displayString = getButtonText();
            saveBtn.enabled = !mode.equals(defaultMode);
        } else if (button.id == saveBtn.id) {
            packMode.set(mode);
            configuration.save();
            Minecraft.getMinecraft().displayGuiScreen(this.lastScreen);
        } else {
            Minecraft.getMinecraft().displayGuiScreen(this.lastScreen);
        }
    }
}
