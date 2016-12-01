package mail.ru.githubclient.proto;

public class User {
    private String login;
    private String avatarUrl;

    User(String l, String ava) {
        this.login = l;
        this.avatarUrl = ava;
    }

    String getLogin() {
        return login;
    }

    String getAvatarUrl() {
        return avatarUrl;
    }
}
