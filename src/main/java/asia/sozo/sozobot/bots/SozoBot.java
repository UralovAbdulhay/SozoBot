package asia.sozo.sozobot.bots;

import asia.sozo.sozobot.dto.UserSaveResponse;
import asia.sozo.sozobot.entity.TgUser;
import asia.sozo.sozobot.service.ButtonService;
import asia.sozo.sozobot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeChat;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.*;
import java.util.regex.Pattern;

import static asia.sozo.sozobot.entity.UserState.*;
import static asia.sozo.sozobot.utils.UserUtils.*;

@Component
@RequiredArgsConstructor
public class SozoBot extends TelegramLongPollingBot {

    private final UserService userService;
    private final ButtonService buttonService;


    @Value("${telegram.bot.admin.token}")
    private String token;

    @Value("${telegram.bot.admin.username}")
    private String botUserName;

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return token;
    }


    private List<BotCommand> botCommands;


    @SneakyThrows
    @Override
    public  synchronized void onUpdateReceived(Update update) {

        new Thread(() -> {


            UserSaveResponse response = userService.save(update);
            TgUser tgUser = response.getUser();


            if (update.hasMessage()) {

                Message message = update.getMessage();
                System.out.println(message.getFrom().getId());

                if (message.isCommand()) {
                    String command = message.getText().split(" ")[0];

                    switch (command) {
                        case _START_COMMAND: {
                            deleteKeyboardButtonAndSend("Assaslomu alaykum, SOZO botga xush kelibsiz!", tgUser.getChatIdString());
                            try {
                                initCommands(tgUser.getChatIdString());
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            doStart(tgUser);
                            tgUser.setState(HOME);
                            break;
                        }
                        case _TIMETABLE: {
                            sendTimeTable(tgUser.getChatIdString());
                            break;
                        }
                        case _LOCATION: {
                            sendLocation(tgUser.getChatIdString(), tgUser.getState().equals(HOME));
                            break;
                        }
                        case _MASTER_CLASS: {
                            tgUser.setState(MASTER_CLASS);
                            sendMasterClass(tgUser.getChatIdString());
                            break;
                        }
                        case _MERCH: {

                            tgUser.setState(MERCH);
                            sendMerch(tgUser.getChatIdString());
                            break;
                        }
                        case _SOZO_INFO: {
                            sendInfo(tgUser.getChatIdString());
                            break;
                        }
                    }

                } else if (message.hasText()) {

                    String text = message.getText();

                    System.out.println(text + " -> " + tgUser.getState());

                    switch (tgUser.getState()) {
                        case MASTER_CLASS: {
                            switch (text) {
                                case "\uD83C\uDFE0 Дамой": {
                                    tgUser.setState(HOME);
                                    doStart(tgUser);
                                    break;
                                }
                                case "Отнашение до брака": {

                                    String mass = "КОНФЕРЕНЦИЯ В САМОМ РАЗГАРЕ\uD83D\uDD25\n" +
                                            "#пятьстранодносердце \n" +
                                            "\n" +
                                            "Если ты по какой-то причине не смог присуствовать на SOZO, мы подготовили для тебя прямой эфир! \uD83D\uDE4C\uD83C\uDFFB \n" +
                                            "\n" +
                                            "Вот ссылка: https://youtu.be/8s-NcxbABV8\n" +
                                            "\n" +
                                            "#sozo";
                                    sendMessage(mass, tgUser.getChatIdString());

                                    break;
                                }

                                case "О кофе": {
                                    String mass = "КОНФЕРЕНЦИЯ В САМОМ РАЗГАРЕ\uD83D\uDD25\n" +
                                            "#пятьстранодносердце \n" +
                                            "\n" +
                                            "Если ты по какой-то причине не смог присуствовать на SOZO, мы подготовили для тебя прямой эфир! \uD83D\uDE4C\uD83C\uDFFB \n" +
                                            "\n" +
                                            "Вот ссылка: https://youtu.be/8s-NcxbABV8\n" +
                                            "\n" +
                                            "# sozo";
                                    sendMessage(mass, tgUser.getChatIdString());
                                    break;
                                }
                                case "Медиа и SMM": {
                                    String mass = "КОНФЕРЕНЦИЯ В САМОМ РАЗГАРЕ\uD83D\uDD25\n" +
                                            "#пятьстранодносердце \n" +
                                            "\n" +
                                            "Если ты по какой-то причине не смог присуствовать на SOZO, мы подготовили для тебя прямой эфир! \uD83D\uDE4C\uD83C\uDFFB \n" +
                                            "\n" +
                                            "Вот ссылка: https://youtu.be/8s-NcxbABV8\n" +
                                            "\n" +
                                            "#sozo ";
                                    sendMessage(mass, tgUser.getChatIdString());
                                    break;
                                }
                                case "Молодежная миссия": {
                                    String mass = "КОНФЕРЕНЦИЯ В САМОМ РАЗГАРЕ\uD83D\uDD25\n" +
                                            "#пятьстранодносердце \n" +
                                            "\n" +
                                            "Если ты по какой-то причине не смог присуствовать на SOZO, мы подготовили для тебя прямой эфир! \uD83D\uDE4C\uD83C\uDFFB \n" +
                                            "\n" +
                                            "Вот ссылка: https://youtu.be/8s-NcxbABV8\n" +
                                            "\n" +
                                            "#soz o";
                                    sendMessage(mass, tgUser.getChatIdString());
                                    break;
                                }
                            }
                            break;
                        }
                        case MERCH: {
                            switch (text) {
                                case "\uD83C\uDFE0 Дамой": {
                                    tgUser.setState(HOME);
                                    doStart(tgUser);
                                    break;
                                }

                                case "Xudi": {
                                    sendMerch(tgUser);
                                    break;
                                }
                                case "Fudbolka": {
                                    sendMerch(tgUser);

                                    break;
                                }
                                case "Naski": {
                                    sendMerch(tgUser);

                                    break;
                                }
                                case "Kepka": {
                                    sendMerch(tgUser);

                                    break;
                                }
                            }
                            break;
                        }
                        case HOME: {
                            switch (text) {
                                case "\uD83D\uDCC5 Jadval": {
                                    sendTimeTable(tgUser.getChatIdString());
                                    break;
                                }
                                case "\uD83D\uDCCD Location": {
                                    sendLocation(tgUser.getChatIdString(), tgUser.isState(HOME));
                                    break;
                                }
                                case "\uD83C\uDFBD Merch": {
                                    tgUser.setState(MERCH);
                                    sendMerch(tgUser.getChatIdString());
                                    break;
                                }
                                case "\uD83D\uDC68\u200D\uD83C\uDFEB Master class": {
                                    tgUser.setState(MASTER_CLASS);
                                    sendMasterClass(tgUser.getChatIdString());
                                    break;
                                }
                                case "❓ What is SOZO ❓": {
                                    sendInfo(tgUser.getChatIdString());
                                    break;
                                }
                            }
                        }
                    }


                } else if (message.hasContact()) {

                }

            } else if (update.hasCallbackQuery()) {

                String id = update.getCallbackQuery().getData();
                System.out.println(id);
                switch (tgUser.getState()) {
                    case LANG: {
                        tgUser.setLang(id);
                        break;
                    }
//                case :
                }

            }

            userService.save(tgUser);

        }).start();

    }

    private synchronized  void sendMerch(TgUser tgUser) {
        InputFile file = new InputFile(new File("imgs/photo_2022-08-17_00-05-52.jpg"));
//        InputFile file = new InputFile("AgACAgIAAxkDAAIB-WL8E-Xg6lWEHS3c4RUym1JT3Bm9AAI7xDEbYefhSxfsZg8aBsYiAQADAgADcwADKQQ.jpg");


        SendPhoto photo = new SendPhoto();
        photo.setChatId(tgUser.getChatIdString());
        photo.setPhoto(file);

        photo.setCaption("М Е Р Ч\n" +
                "\n" +
                "Фирменный мерч SOZO - это отличное напоминание о конференции, которое останется с тобой ещё надолго! \uD83D\uDE0D \n" +
                "\n" +
                "P.S Это будет крутым подарком для твоего друга, который не смог поехать на SOZO \uD83E\uDD2B \n" +
                "\n" +
                "ВСЕ ЦЕНЫ УКАЗАНЫ В ТЕНГЕ ❗\n" +
                "\n" +
                "\uD83D\uDCCD| Рихарда Зорге 14а, г. Алматы, Казахстан\n" +
                "\uD83D\uDDD3| 16-19 августа\n" +
                "\uD83D\uDCF1| @sozoconf\n" +
                "\uD83C\uDF0E| www.sozoconf.asia\n" +
                "#⃣| #sozoconf\n" +
                "\n" +
                "#sozoconf #centralasia #soon #скоро");
        System.out.println(sendMessage(photo, tgUser.getChatIdString()));
    }


//    photo=[PhotoSize(fileId=AgACAgIAAxkDAAIB-WL8E-Xg6lWEHS3c4RUym1JT3Bm9AAI7xDEbYefhSxfsZg8aBsYiAQADAgADcwADKQQ, fileUniqueId=AQADO8QxG2Hn4Ut4, width=90, height=26, fileSize=1156, filePath=null),
//    PhotoSize(fileId=AgACAgIAAxkDAAIB-WL8E-Xg6lWEHS3c4RUym1JT3Bm9AAI7xDEbYefhSxfsZg8aBsYiAQADAgADbQADKQQ, fileUniqueId=AQADO8QxG2Hn4Uty, width=320, height=91, fileSize=11438, filePath=null),
//    PhotoSize(fileId=AgACAgIAAxkDAAIB-WL8E-Xg6lWEHS3c4RUym1JT3Bm9AAI7xDEbYefhSxfsZg8aBsYiAQADAgADeAADKQQ, fileUniqueId=AQADO8QxG2Hn4Ut9, width=800, height=227, fileSize=43212, filePath=null),
//    PhotoSize(fileId=AgACAgIAAxkDAAIB-WL8E-Xg6lWEHS3c4RUym1JT3Bm9AAI7xDEbYefhSxfsZg8aBsYiAQADAgADeQADKQQ, fileUniqueId=AQADO8QxG2Hn4Ut-, width=1280, height=364, fileSize=57112, filePath=null)]


    private  synchronized void doStart(TgUser tgUser) {


      /*
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

        List<List<Map<String, String>>> lists = new ArrayList<>(7);

        lists.add(Collections.singletonList(newMap("ru_ru", "\uD83C\uDDF7\uD83C\uDDFA Русский")));
        lists.add(Collections.singletonList(newMap("uz_lat", "\uD83C\uDDFA\uD83C\uDDFF O'zbekcha")));
        lists.add(Collections.singletonList(newMap("kar_kar", "\uD83C\uDDFA\uD83C\uDDFF Қарақалпақша")));
        lists.add(Collections.singletonList(newMap("kaz_kaz", "\uD83C\uDDF0\uD83C\uDDFF Қазақша")));
        lists.add(Collections.singletonList(newMap("kir_kir", "\uD83C\uDDF0\uD83C\uDDEC Кыргызча")));
        lists.add(Collections.singletonList(newMap("taj_taj", "\uD83C\uDDF9\uD83C\uDDEF Тоҷикӣ")));


        List<List<InlineKeyboardButton>> inlineButtonBoard = buttonService.createInlineButtonBoard(lists);
        markupInline.setKeyboard(inlineButtonBoard);
*/


        SendMessage sendMessage = new SendMessage(tgUser.getChatIdString(), "\uD83D\uDC47 pastdagi buyruqlardan birini tanlang: ");
//        sendMessage.setReplyMarkup(markupInline);

        List<List<String>> lists = new ArrayList<>();
        lists.add(Arrays.asList("\uD83D\uDCC5 Jadval", "\uD83D\uDCCD Location"));
        lists.add(Arrays.asList("\uD83D\uDC68\u200D\uD83C\uDFEB Master class", "\uD83C\uDFBD Merch"));
        lists.add(Collections.singletonList("❓ What is SOZO ❓"));

        ReplyKeyboardMarkup markup = buttonService.createKeyboardMarkup(lists);
        markup.setResizeKeyboard(true);
        sendMessage.setReplyMarkup(markup);

        sendMessage(sendMessage);
//        tgUser.setState(LANG);
    }



    private synchronized  Map<String, String> newMap(String key, String value) {
        Map<String, String> map = new HashMap<String, String>(1);
        map.put(key, value);
        return map;
    }


    private synchronized  void deleteKeyboardButtonAndSend(String text, String chatId) {

        ReplyKeyboardRemove remove = new ReplyKeyboardRemove(true);
        SendMessage sendMessage = new SendMessage(chatId, text);
        sendMessage.setReplyMarkup(remove);
        sendMessage.setProtectContent(true);
        sendMessage(sendMessage);

    }

    private synchronized  void sendInfo(String chatId) {
        String message = "SOZO - масштабная молодежная христианская конференция Центральной Азии\n" +
                "\n" +
                "\uD83D\uDDD3 | 16-19 августа\n" +
                "\uD83D\uDCCD | Рихарда Зорге, 14а г. Алматы, Казахстан\n" +
                "\uD83D\uDCF1 | insta: @sozoconf\n" +
                "\uD83C\uDF0F | www.sozoconf.asia\n" +
                "#️⃣ | #sozoconf\n" +
                "\n" +
                "SOZO происходит от греческого глагола  σωζω (созо)\n" +
                "В Новом Завете это слово используется более 100 раз, обычно переводится как:\n" +
                " \n" +
                "Исцелить\n" +
                "Сохранить\n" +
                "Спасти";
        sendMessage(message, chatId);
    }

    private  synchronized void sendMasterClass(String chatId) {

        List<List<String>> list = new ArrayList<>(5);
        list.add(Arrays.asList("Отнашение до брака", "О кофе"));
        list.add(Arrays.asList("Молодежная миссия", "Медиа и SMM"));
        list.add(Collections.singletonList("\uD83C\uDFE0 Дамой"));

        ReplyKeyboardMarkup keyboardMarkup = buttonService.createKeyboardMarkup(list);
        keyboardMarkup.setResizeKeyboard(true);

        sendReplayMarkup("Master Classlarni tanlang:", keyboardMarkup, chatId);

    }


    private synchronized  void sendMerch(String chatId) {

        List<List<String>> list = new ArrayList<>(5);
        list.add(Arrays.asList("Xudi", "Fudbolka"));
        list.add(Arrays.asList("Kepka", "Naski"));
        list.add(Collections.singletonList("\uD83C\uDFE0 Дамой"));

        ReplyKeyboardMarkup keyboardMarkup = buttonService.createKeyboardMarkup(list);
        keyboardMarkup.setResizeKeyboard(true);

        sendReplayMarkup("Merch ni tanlang:", keyboardMarkup, chatId);

    }

    private synchronized void sendReplayMarkup(String text, ReplyKeyboardMarkup markup, String chatId) {
        SendMessage message = new SendMessage(chatId, text);
        message.setReplyMarkup(markup);
        sendMessage(message);
    }

    private  synchronized void sendTimeTable(String chatId) {
        String message = "12 ДНЕЙ ДО КОНФЕРЕНЦИИ SOZO \uD83D\uDCA5\n" +
                "\n" +
                "Старший пастор церкви «Almaty Church» - Иван Крюков, приглашает лично тебя стать частью того, что Бог делает здесь в Центральной Азии! \n" +
                "\n" +
                "\uD83D\uDCCD| г. Алматы, Казахстан \n" +
                "\uD83D\uDDD3| 16-19 августа\n" +
                "\uD83D\uDCF1| @sozoconf\n" +
                "\uD83C\uDF0E| www.sozoconf.asia\n" +
                "#⃣| #sozoconf\n" +
                "\n" +
                "#sozoconf #centralasia #soon";

        sendMessage(message, chatId);
//        deleteKeyboardButtonAndSend(message, chatId);
    }


    private synchronized  Message sendMessage(String text, String chatId) {
        try {
            SendMessage message = new SendMessage(chatId, text);
            message.enableHtml(true);
            return execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return null;
        }
    }


    private  synchronized Message sendMessage(SendPhoto photo, String chatId) {
        try {
            return execute(photo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    private synchronized  Message sendMessage(SendMessage message) {
        try {
            return execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return null;
        }
    }


    private synchronized  Message sendLocation(String chatId, boolean isHome) {
        try {

            List<List<Map<String, String>>> maps = new ArrayList<>();
            Map<String, String> mapsList = new HashMap(6);
            mapsList.put(GOOGLE, "https://goo.gl/maps/kEKyXFdHrpPFYhgu8");
            mapsList.put(YANDEX, "https://yandex.com/navi/-/CCUR6GF-LD");
            mapsList.put(GIS2, "https://go.2gis.com/6qllv");

            mapsList.forEach((e, k) -> {
                HashMap<String, String> map = new HashMap<>();
                map.put(e, k);
                maps.add(Collections.singletonList(map));
            });

            if (isHome) {
                sendMessage("Locationni tanlang \uD83D\uDC47:", chatId);
            } else {
                deleteKeyboardButtonAndSend("Locationni tanlang \uD83D\uDC47:", chatId);
            }

            List<List<InlineKeyboardButton>> inlineButtonBoard = buttonService.createInlineButtonBoard(maps, true);

            InlineKeyboardMarkup markup = new InlineKeyboardMarkup(inlineButtonBoard);

            SendLocation sendLocation = new SendLocation(chatId, 43.337667000000003, 76.953017000000003);
            sendLocation.setReplyMarkup(markup);


            return execute(sendLocation);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return null;
        }
    }


    private synchronized  void sendEditMessage(EditMessageText message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    private  synchronized boolean deleteMessage(DeleteMessage message) {
        try {
            return execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return false;
        }
    }


    private synchronized  boolean deleteMessage(String chatId, Message message) {
        try {
            DeleteMessage deleteMessage = new DeleteMessage(chatId, message.getMessageId());
            return execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return false;
        }
    }


    private synchronized  void initCommands(String chatId) throws TelegramApiException {
        botCommands = (List<BotCommand>) Arrays.asList(
                new BotCommand(_START_COMMAND, "Restart bot"),
                new BotCommand(_TIMETABLE, "Jadval"),
                new BotCommand(_LOCATION, "Location"),
                new BotCommand(_MASTER_CLASS, "Master class"),
                new BotCommand(_MERCH, "Merch"),
                new BotCommand(_SOZO_INFO, "What is SOZO?")

        );
        BotCommandScopeChat scopeChat = new BotCommandScopeChat(chatId);
        execute(new SetMyCommands(botCommands, scopeChat, "ru"));
    }


    private synchronized boolean validateContact(String phone) {
        Pattern pattern = Pattern.compile("^(\\+|)998(9[0-9]|6[125679]|7[125679]|8[8]|3[3])[0-9]{7}$");
        return pattern.matcher(phone).matches();
    }

}
