package org.example.command;

import java.util.HashMap;
import java.util.Map;

import static org.example.command.AboutCommand.ABOUT_HELP;
import static org.example.command.EchoCommand.ECHO_HELP;
import static org.example.command.StartCommand.START_HELP;
import static org.example.command.CommandName.*;

public class GetHelpForCommand {
    public String getHelp(String sendCommand) {
        Map<String, String> command = new HashMap<String, String>();
        command.put(START.getCommandName(),  START_HELP);
        command.put(ABOUT.getCommandName(), ABOUT_HELP);
        command.put(ECHO.getCommandName(), ECHO_HELP);
        String response = command.get(sendCommand);
        if(response != null){
            return response;
        }
        return "Эту команду обелили HELP`ом";
    }
}
