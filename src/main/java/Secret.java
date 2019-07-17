import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Secret {

    private static volatile Secret secret;
    private SecretJson secretJson;

    private Secret(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File(getClass().getClassLoader().getResource("secret.json").getFile());
            secretJson = objectMapper.readValue(file, SecretJson.class);
            System.out.println("Read config: " + secretJson.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Secret getSecret(){
        if(secret == null){
            synchronized (Secret.class){
                if(secret == null) secret = new Secret();
            }
        }
        return secret;
    }

    public String getName() {
        return secretJson.name;
    }

    public String getToken() {
        return secretJson.token;
    }

    public String getHost() {
        return secretJson.host;
    }

    public int getPort() {
        return secretJson.port;
    }

    public String getUser() {
        return secretJson.user;
    }

    public String getPass() {
        return secretJson.pass;
    }
}
