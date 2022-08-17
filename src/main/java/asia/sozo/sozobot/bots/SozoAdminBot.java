package asia.sozo.sozobot.bots;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static asia.sozo.sozobot.utils.UserUtils.*;


//@Component
public class SozoAdminBot extends TelegramLongPollingBot {

    //    @Value("${telegram.bot.admin.token}")
    private String token;

    //    @Value("${telegram.bot.admin.username}")
    private String botUserName;

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return null;
    }


//    private List<BotCommand> botCommands;


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {


    }


    private Message sendMessage(String text, String chatId) {
        try {
            SendMessage message = new SendMessage(chatId, text);
            message.enableHtml(true);
            return execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Message sendMessage(SendMessage message) {
        try {
            return execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void sendEditMessage(EditMessageText message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    private boolean deleteMessage(DeleteMessage message) {
        try {
            return execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return false;
        }
    }


    private boolean deleteMessage(String chatId, Message message) {
        try {
            DeleteMessage deleteMessage = new DeleteMessage(chatId, message.getMessageId());
            return execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return false;
        }
    }

/*

    private void initCommands(String chatId) throws TelegramApiException {
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
*/


    private boolean validateContact(String phone) {
        Pattern pattern = Pattern.compile("^(\\+|)998(9[0-9]|6[125679]|7[125679]|8[8]|3[3])[0-9]{7}$");
        return pattern.matcher(phone).matches();
    }

}
