package mail.ru.githubclient;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import mail.ru.githubclient.proto.User;
import mail.ru.githubclient.ui.DefaultHolder;
import mail.ru.githubclient.ui.UserView;
import mail.ru.githubclient.ui.UserView_;

@EBean
class UsersAdapter extends RecyclerView.Adapter<DefaultHolder<User, UserView>> {
    private final List<User> users = new ArrayList<>();

    void setUsers(List<User> users) {
        this.users.clear();
        this.users.addAll(users);
        notifyDataSetChanged();
    }

    @Override
    public DefaultHolder<User, UserView> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DefaultHolder<>(UserView_.build(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(DefaultHolder<User, UserView> holder, int position) {
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

}
