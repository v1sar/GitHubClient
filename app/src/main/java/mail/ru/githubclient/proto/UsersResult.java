package mail.ru.githubclient.proto;

import java.util.List;

/**
 * Created by nikita on 01.12.16.
 */

public class UsersResult {

    private int total_count = 0;
    private boolean incomplete_results = false;
    private List<User> items;

    public List<User> getItems() {
        return this.items;
    }

    @Override
    public String toString() {
        return "UsersResult{" +
                "total_count=" + total_count +
                ", incomplete_results=" + incomplete_results +
                ", items=" + items +
                '}';
    }
}
