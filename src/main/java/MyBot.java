import com.pi4j.io.gpio.*;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class MyBot extends TelegramLongPollingBot {

    protected MyBot() {
        super();
    }


    protected MyBot(DefaultBotOptions botOptions) {
        super(botOptions);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chartId = update.getMessage().getChatId();
            String messText = update.getMessage().getText();

            if (messText.equals("/start")) {
                sendMsg(chartId, messText);
            } else if (messText.equals("Row 1 Button 1")) {
                new PinSwitch(RaspiPin.GPIO_00).run();
                sendMsg(chartId, "Pump 1");
            } else if (messText.equals("Row 1 Button 2")) {
                new PinSwitch(RaspiPin.GPIO_01).run();
                sendMsg(chartId, "Pump 2");
            }else if (messText.equals("Row 1 Button 3")) {
                new PinSwitch(RaspiPin.GPIO_02).run();
                sendMsg(chartId, "Pump 3");
            }else if (messText.equals("Row 2 Button 1")) {
                new PinSwitch(RaspiPin.GPIO_03).run();
                sendMsg(chartId, "Pump 4");
            }else if (messText.equals("Row 2 Button 2")) {
                new PinSwitch(RaspiPin.GPIO_04).run();
                sendMsg(chartId, "Pump 5");
            }
            else {
                sendKeybord(chartId);
            }
        }
    }

    public synchronized void sendMsg(Long chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            execute(sendMessage);
            System.out.println("Message " + "[" + chatId + "] " + s);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendKeybord(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText("Here is your keyboard");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        // Create the keyboard (list of keyboard rows)
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Create a keyboard row
        KeyboardRow row = new KeyboardRow();
        // Set each button, you can also use KeyboardButton objects if you need something else than text
        row.add("Row 1 Button 1");
        row.add("Row 1 Button 2");
        row.add("Row 1 Button 3");
        // Add the first row to the keyboard
        keyboard.add(row);
        // Create another keyboard row
        row = new KeyboardRow();
        // Set each button for the second line
        row.add("Row 2 Button 1");
        row.add("Row 2 Button 2");
        // Add the second row to the keyboard
        keyboard.add(row);
        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        sendMessage.setReplyMarkup(keyboardMarkup);


        try {
            execute(sendMessage);
            System.out.println("Keybord " + "[" + chatId + "]");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return Secret.getSecret().getName();
    }

    @Override
    public String getBotToken() {
        return Secret.getSecret().getToken();
    }

}
