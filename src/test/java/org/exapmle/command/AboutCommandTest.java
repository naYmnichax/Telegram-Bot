package org.exapmle.command;

import org.example.command.AboutCommand;
import org.example.command.Command;
import org.junit.jupiter.api.DisplayName;

import static org.example.command.CommandName.ABOUT;
import static org.example.command.AboutCommand.ABOUT_MESSAGE;

@DisplayName("Unit-level testing for AboutCommand")
public class AboutCommandTest extends AbstractCommandTest {

    @Override
    String getCommandName() {
        return ABOUT.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return ABOUT_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new AboutCommand(sendBotMessageService);
    }
}
