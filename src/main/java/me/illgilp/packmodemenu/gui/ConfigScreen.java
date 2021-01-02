package me.illgilp.packmodemenu.gui;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

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
        acceptedModes = configuration.get("general", "acceptedModes", new String[]{"normal", "expert"});

    }

    @Override
    public void initGui() {
        if (firstInit) {
            mode = packMode.getString();
            defaultMode = packMode.getString();
        }
        switchBtn = new GuiButtonExt(0, width / 2 - 100, height / 6, getButtonText());
        buttonList.add(switchBtn);
        saveBtn = new GuiButtonExt(1, this.width / 2 - 155, this.height - 50, 150, 20, I18n.format("packmodemenu.options.save"));
        saveBtn.enabled = !mode.equals(defaultMode);
        this.buttonList.add(saveBtn);
        this.buttonList.add(new GuiButtonExt(2, this.width / 2 + 5, this.height - 50, 150, 20, I18n.format("packmodemenu.options.cancel")));

        super.initGui();
        firstInit = false;
    }

    private String getButtonText() {
        String translatedModeName;
        if (I18n.hasKey("packmodemenu.packmode.name." + mode)) {
            translatedModeName = I18n.format("packmodemenu.packmode.name." + mode);
        } else {
            translatedModeName = mode.toUpperCase();
        }
        return I18n.format("packmodemenu.options.pack_mode") + ": " + (!mode.equals(defaultMode) ? "§a" : "") + translatedModeName + (!mode.equals(defaultMode) ? "" : " (" + I18n.format("packmodemenu.options.current") + ")");
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, I18n.format("packmodemenu.options.pack_mode"), this.width / 2, 15, 0xffffff);
        StringBuilder textMultiline = new StringBuilder();
        if (I18n.hasKey("packmodemenu.packmode.desc." + mode)) {
            textMultiline.append(I18n.format("packmodemenu.packmode.desc." + mode).replace("\\n", "\n")).append("\n\n");
        }
        if (!mode.equals(defaultMode)) {
            String[] warningText = I18n.format("packmodemenu.options.warning.restart").split("\\\\n");
            for (String line : warningText) {
                textMultiline.append(ChatFormatting.RED).append(line).append("\n");
            }
        }
        this.drawMultilineString(textMultiline.toString(), this.width / 2, this.height / 6 + 40 - 6, 0xffffff);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
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

    public void drawMultilineString(String textMultiline, int x, int y, int color) {
        String[] texts = textMultiline.split("\\n");
        int offset = 0;
        for (String text : texts) {
            this.drawCenteredString(this.fontRenderer, text, x, y + offset, color);
            offset += 10;
        }
    }
}
