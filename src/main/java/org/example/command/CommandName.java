package org.example.command;

public enum CommandName {
    START("/start"),
    HELP("/help"),
    ABOUT("/about"),
    ECHO("/echo"),
    NO("nocommand"),
    TRANSLATE("/translate"),
    SUPPORTED_LANGUAGES("/supplang");

    private final String commandName;

    CommandName(String commandName){
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
