package me.illgilp.packmodemenu.lang;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Minecraft;

public class LanguageManager {

    private Map<Locale, Language> languages = new ConcurrentHashMap<>();
    private List<Locale> unavailableLanguages = new ArrayList<>();

    public LanguageManager() {
        Language language = new Language(Locale.US);
        if (!language.load()) {
            throw new RuntimeException("cannot load en_us language file");
        }
        languages.put(language.getLocale(), language);
    }

    public String getTranslation(String key) {
        Locale current = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getJavaLocale();
        Language language;
        if (!languages.containsKey(current)) {
            if (unavailableLanguages.contains(current)) {
                language = languages.get(Locale.US);
            } else {
                Language language2 = new Language(current);
                if (!language2.load()) {
                    language = languages.get(Locale.US);
                    unavailableLanguages.add(current);
                } else {
                    languages.put(current, language2);
                    language = language2;
                }
            }
        } else {
            language = languages.get(current);
        }

        String translation = language.getTranslation(key);

        if (translation != null) {
            return translation;
        }

        language = languages.get(Locale.US);

        translation = language.getTranslation(key);

        if (translation != null) {
            return translation;
        } else {
            throw new RuntimeException("cannot find en_us translation for '" + key + "'");
        }
    }
}
