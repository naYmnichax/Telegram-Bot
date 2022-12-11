package org.example.TelegramBot;

import java.util.HashMap;
import java.util.Map;

public class Command {
    public String SetCommand(String textCommand, String textUser, String name) {
        Map<String, String> command = new HashMap<String, String>();
        command.put("/start", Start(name));
        command.put("/about", About());
        command.put("/help", Help());
        command.put("/echo", Echo(textUser));

        for (String instruction : command.keySet()) {
            if (instruction.equals(textCommand)) {
                return command.get(instruction);
            }
        }
        return "There is no such command. Make sure the command is written correctly or use the /help command.";
    }

    private static String Start(String username) {
        return "Hello, " + username + ".\nWrite /help to find out the available bot commands";
    }

    private static String About() {
        return "It was created by Matvey Matus";
    }

    private static String Help() {
        return """
                /about - Will tell you about who wrote it.
                /echo - After the command, specify the sentence/phrase that you want to duplicate, separating them with a space.
                /help - Write /help to find out the available bot commands.
                """;
    }

    private static String Echo(String msg) {
        if (msg.equals("")){
            return "No message has been entered. Use the /help command.";
        }
        return "You sent: " + msg;
    }
}