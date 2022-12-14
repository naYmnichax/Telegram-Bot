package org.example.command;

import org.example.translateAPI.SupportedLanguages;
import org.example.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.example.command.CommandName.SUPPORTED_LANGUAGES;

public class SupLangCommand implements Command {
    private final SendBotMessageService sendBotMessageService;

    public final static String  SUPP_LANG_MESSAGE = "<b>Поддерживаемые языки:</b>\n\n%s";

    public final static String SUPP_LANG_HELP = String.format("%s - выведет поддерживаемые языки", SUPPORTED_LANGUAGES.getCommandName());

    public SupLangCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        SupportedLanguages supportedLanguages = new SupportedLanguages();
        StringBuilder langs = new StringBuilder();
        supportedLanguages.supportedLanguages.forEach((shortLang, LongLang) -> langs.append(shortLang).append(" - ").append(LongLang).append("\n"));
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),String.format(SUPP_LANG_MESSAGE, langs));
    }
}
