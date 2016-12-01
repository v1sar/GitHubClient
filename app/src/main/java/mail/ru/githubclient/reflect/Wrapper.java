package mail.ru.githubclient.reflect;

public interface Wrapper<T> {

    T delegate();
    void unregister();

}
