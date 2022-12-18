package org.example.translateAPI;

import java.util.HashMap;
import java.util.Map;

public class SupportedLanguages {
    public Map<String, String> supportedLanguages = new HashMap<>();

    public boolean checkLanguages(String langFrom, String langTo){
        return supportedLanguages.containsKey(langFrom) && supportedLanguages.containsKey(langTo);
    }
    public SupportedLanguages(){
        supportedLanguages.put("en","Английский");
        supportedLanguages.put("ru","Русский");
        supportedLanguages.put("kk","Казахский");
        supportedLanguages.put("de","Немецкий");
    }
}
