package mail.ru.githubclient;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import mail.ru.githubclient.proto.User;

@EBean
class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserHolder> {
    private final List<User> users = new ArrayList<>();
    private final LayoutInflater inflater;

    void setUsers(List<User> users) {
        this.users.clear();
        this.users.addAll(users);
        notifyDataSetChanged();
    }

    UsersAdapter(final Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserHolder(inflater.inflate(R.layout.user_item, parent, false));
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class UserHolder extends RecyclerView.ViewHolder {
        UserHolder(View itemView) {
            super(itemView);
        }

        void bind(User user) {

        }
    }
}
