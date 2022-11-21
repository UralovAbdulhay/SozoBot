package asia.sozo.sozobot.service;

import lombok.RequiredArgsConstructor;
        import org.springframework.stereotype.Service;
        import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
        import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
        import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.Map;
        import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ButtonService {

    public List<InlineKeyboardButton> createInlineButtonRow(List<Map<String, String>> map) {

        List<InlineKeyboardButton> buttons = new ArrayList<>();


        map.forEach(
                e -> {
                    e.keySet().stream().filter(Objects::nonNull).limit(1).forEach(key -> {
                        InlineKeyboardButton button = new InlineKeyboardButton();
                        button.setText(e.get(key));
                        button.setCallbackData(key);
                        buttons.add(button);
                    });
                }
        );

        return buttons;
    }

    public List<InlineKeyboardButton> createInlineButtonRow(List<Map<String, String>> map, boolean isUrl) {

        List<InlineKeyboardButton> buttons = new ArrayList<>();


        map.forEach(
                e -> {
                    e.keySet().stream().filter(Objects::nonNull).limit(1).forEach(key -> {
                        InlineKeyboardButton button = new InlineKeyboardButton();
                        button.setText(e.get(key));
                        button.setCallbackData(key);
                        if (isUrl) {
                            button.setUrl(e.get(key));
                        }
                        buttons.add(button);
                    });
                }
        );

        return buttons;
    }

    public List<List<InlineKeyboardButton>> createInlineButtonBoard(List<List<Map<String, String>>> map) {

        List<List<InlineKeyboardButton>> board = new ArrayList<>();
        map.forEach(e -> {
            if (!e.isEmpty()) {
                board.add(createInlineButtonRow(e));
            }
        });

        return board;
    }

    public List<List<InlineKeyboardButton>> createInlineButtonBoard(List<List<Map<String, String>>> map, boolean isUrl) {

        List<List<InlineKeyboardButton>> board = new ArrayList<>();
        map.forEach(e -> {
            if (!e.isEmpty()) {
                board.add(createInlineButtonRow(e, isUrl));
            }
        });

        return board;
    }


    public KeyboardRow createKeyboardButtonRow(List<String> strings) {
        KeyboardRow row = new KeyboardRow();
        strings.forEach(row::add);
        return row;
    }

    public List<KeyboardRow> createKeyboardButtonBoard(List<List<String>> map) {
        List<KeyboardRow> keyboard = new ArrayList<>();
        map.forEach(e -> {
            keyboard.add(createKeyboardButtonRow(e));
        });

        return keyboard;
    }

    public ReplyKeyboardMarkup createKeyboardMarkup(List<List<String>> lists) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(createKeyboardButtonBoard(lists));
        return keyboardMarkup;
    }


}
