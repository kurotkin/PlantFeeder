import com.fasterxml.jackson.databind.ObjectMapper;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;

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
            Long chartId = update.getMessage().getChatId();
            String messText = update.getMessage().getText();
            SendMessage message = new SendMessage().setChatId(chartId).setText(messText);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File(getClass().getClassLoader().getResource("secret.json").getFile());
            Secret secret = objectMapper.readValue(file, Secret.class);
            return secret.getName();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getBotToken() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File(getClass().getClassLoader().getResource("secret.json").getFile());
            Secret secret = objectMapper.readValue(file, Secret.class);
            return secret.getToken();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
