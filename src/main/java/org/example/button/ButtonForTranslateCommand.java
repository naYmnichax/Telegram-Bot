package org.example.button;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ButtonForTranslateCommand {
    public void createButtonLanguageFrom(SendMessage message) {

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine2 = new ArrayList<>();

        var enLang = new InlineKeyboardButton();
        enLang.setText("Английский  " + EmojiParser.parseToUnicode(":gb:"));
        enLang.setCallbackData("LanguageFrom_EN");

        var deLang = new InlineKeyboardButton();
        deLang.setText("Немецкий  " + EmojiParser.parseToUnicode(":de:"));
        deLang.setCallbackData("LanguageFrom_DE");

        var ruLang = new InlineKeyboardButton();
        ruLang.setText("Русский  " + EmojiParser.parseToUnicode(":ru:"));
        ruLang.setCallbackData("LanguageFrom_RU");

        var kkLang = new InlineKeyboardButton();
        kkLang.setText("Казахский  " + EmojiParser.parseToUnicode(":kz:"));
        kkLang.setCallbackData("LanguageFrom_KZ");

        rowInLine1.add(enLang);
        rowInLine1.add(deLang);
        rowInLine2.add(ruLang);
        rowInLine2.add(kkLang);

        rowsLine.add(rowInLine1);
        rowsLine.add(rowInLine2);

        markupInLine.setKeyboard(rowsLine);
        message.setReplyMarkup(markupInLine);
    }

    public void createButtonLanguageTo (EditMessageText message) {

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine2 = new ArrayList<>();

        var enLang = new InlineKeyboardButton();
        enLang.setText("Английский  " + EmojiParser.parseToUnicode(":gb:"));
        enLang.setCallbackData("LanguageTo_EN");

        var deLang = new InlineKeyboardButton();
        deLang.setText("Немецкий  " + EmojiParser.parseToUnicode(":de:"));
        deLang.setCallbackData("LanguageTo_DE");

        var ruLang = new InlineKeyboardButton();
        ruLang.setText("Русский  " + EmojiParser.parseToUnicode(":ru:"));
        ruLang.setCallbackData("LanguageTo_RU");

        var kkLang = new InlineKeyboardButton();
        kkLang.setText("Казахский  " + EmojiParser.parseToUnicode(":kz:"));
        kkLang.setCallbackData("LanguageTo_KZ");

        rowInLine1.add(enLang);
        rowInLine1.add(deLang);
        rowInLine2.add(ruLang);
        rowInLine2.add(kkLang);

        rowsLine.add(rowInLine1);
        rowsLine.add(rowInLine2);

        markupInLine.setKeyboard(rowsLine);
        message.setReplyMarkup(markupInLine);
    }
}
