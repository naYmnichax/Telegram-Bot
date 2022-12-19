package org.example.translateAPI;

import java.util.HashMap;
import java.util.Map;

public class SupportedLanguages {
    public static Map<String, String> supportedLanguages = new HashMap<>();

    public static boolean checkLanguages(String langFrom){
        return supportedLanguages.containsKey(langFrom);
    }
    public SupportedLanguages(){
        supportedLanguages.put("en","Английский");
        supportedLanguages.put("ru","Русский");
        supportedLanguages.put("kk","Казахский");
        supportedLanguages.put("de","Немецкий");
    }
}
