package me.illgilp.packmodemenu.lang;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import me.illgilp.packmodemenu.PackModeMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;

public class Language {

    private Locale locale;

    private Map<String, String> map = new ConcurrentHashMap<>();

    public Language(Locale locale) {
        this.locale = locale;
    }

    public boolean load() {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("assets/" + PackModeMenu.MOD_ID + "/lang/" + locale.toString().toLowerCase() + ".lang");
            if (inputStream == null) {
                return false;
            }
            Properties properties = new Properties();
            properties.load(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            for (Object o : properties.keySet()) {
                if (o instanceof String) {
                    map.put((String) o, properties.getProperty((String) o));
                }
            }

            inputStream.close();
            return true;

        } catch (IOException e) {
            return false;
        }
    }

    public Locale getLocale() {
        return locale;
    }

    public String getTranslation(String key) {
        return map.get(key);
    }
}
