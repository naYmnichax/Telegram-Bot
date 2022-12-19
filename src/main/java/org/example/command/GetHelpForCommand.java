package org.example.command;

import java.util.HashMap;
import java.util.Map;

import static org.example.command.CommandName.*;
import static org.example.command.AboutCommand.ABOUT_HELP;
import static org.example.command.DictionaryCommand.TEXT_HELP;
import static org.example.command.EchoCommand.ECHO_HELP;
import static org.example.command.StartCommand.START_HELP;
import static org.example.command.SupLangCommand.SUPP_LANG_HELP;
import static org.example.command.TranslateCommand.TRANSLATE_HELP;

public class GetHelpForCommand {
    public String getHelp(String sendCommand) {
        Map<String, String> command = new HashMap<>();
        command.put(START.getCommandName(),  START_HELP);
        command.put(ABOUT.getCommandName(), ABOUT_HELP);
        command.put(ECHO.getCommandName(), ECHO_HELP);
        command.put(TRANSLATE.getCommandName(), TRANSLATE_HELP);
        command.put(SUPPORTED_LANGUAGES.getCommandName(), SUPP_LANG_HELP);
        command.put(TEXT_CHECK.getCommandName(),TEXT_HELP);
        String response = command.get(sendCommand);
        if(response != null){
            return response;
        }
        return "Эту команду обделили HELP`ом";
    }
}
