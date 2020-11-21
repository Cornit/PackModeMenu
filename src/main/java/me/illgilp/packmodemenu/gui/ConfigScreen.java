package me.illgilp.packmodemenu.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
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
        mode = packMode.getString();
        defaultMode = packMode.getString();
        switchBtn = new GuiButtonExt(0, width / 2 - 100, height / 6 , getButtonText());
        buttonList.add(switchBtn);
        saveBtn = new GuiButtonExt(1, this.width / 2 - 155, this.height / 6 + 120 - 6, 150, 20, "Save");
        saveBtn.enabled = false;
        this.buttonList.add(saveBtn);
        this.buttonList.add(new GuiButtonExt(2, this.width / 2 + 5, this.height / 6 + 120 - 6, 150, 20, "Cancel"));
        super.initGui();
    }

    private String getButtonText() {
        return "Pack-Mode: " + (!mode.equals(defaultMode) ? "§a" : "") + mode.toUpperCase() + (!mode.equals(defaultMode) ? "" : " (current)");
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, "Pack-Mode", this.width / 2, 15, 16777215);

        if (!mode.equals(defaultMode)) {
            this.drawCenteredString(this.fontRenderer, "After saving you have to restart the game", this.width / 2, this.height / 6 * 3, 0xFF5555);
            this.drawCenteredString(this.fontRenderer, "for the changes to take effect.", this.width / 2, this.height / 6 * 3 + 10, 0xFF5555);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == switchBtn.id) {
            mode = acceptedModes.getStringList()[(new ArrayList<>(Arrays.asList(acceptedModes.getStringList())).lastIndexOf(mode) + 1) % acceptedModes.getStringList().length];
            switchBtn.displayString = getButtonText();
            if (mode.equals(defaultMode)) {
                saveBtn.enabled = false;
            } else {
                saveBtn.enabled = true;
            }
        } else if (button.id == saveBtn.id) {
            packMode.set(mode);
            configuration.save();
            Minecraft.getMinecraft().displayGuiScreen(this.lastScreen);
        } else {
            Minecraft.getMinecraft().displayGuiScreen(this.lastScreen);
        }
    }
}
