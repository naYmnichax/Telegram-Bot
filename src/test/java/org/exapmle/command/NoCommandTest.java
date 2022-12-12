package org.exapmle.command;

import org.example.command.Command;
import org.example.command.NoCommand;

import static org.example.command.CommandName.NO;
import static org.example.command.NoCommand.NO_MESSAGE;

public class NoCommandTest extends AbstractCommandTest{
    @Override
    String getCommandName() {
        return NO.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return NO_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new NoCommand(sendBotMessageService);
    }
}
