public class SecretJson {
    public String name;
    public String token;
    public String host;
    public int port;
    public String user;
    public String pass;

    @Override
    public String toString() {
        return  "{" +
                "name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", user='" + user + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
