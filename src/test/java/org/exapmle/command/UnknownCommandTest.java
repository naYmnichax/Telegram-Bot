package org.exapmle.command;

import org.example.command.Command;
import org.example.command.UnknownCommand;

import static org.example.command.UnknownCommand.UNKNOWN_MESSAGE;

public class UnknownCommandTest extends AbstractCommandTest{
    @Override
    String getCommandName() {
        return "/fgjhdfgdfg";
    }

    @Override
    String getCommandMessage() {
        return UNKNOWN_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new UnknownCommand(sendBotMessageService);
    }
}
