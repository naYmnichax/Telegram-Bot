package org.exapmle.command;

import org.example.command.Command;
import org.example.command.HelpCommand;

import static org.example.command.CommandName.HELP;
import static org.example.command.HelpCommand.HELP_MESSAGE;

public class HelpCommandTest extends AbstractCommandTest{
    @Override
    String getCommandName() {
        return HELP.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return HELP_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new HelpCommand(sendBotMessageService);
    }
}
