package mail.ru.githubclient.ui;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EView;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import mail.ru.githubclient.R;
import mail.ru.githubclient.proto.User;

@EViewGroup(R.layout.user_item)
public class UserView extends FrameLayout implements BindableView<User> {

    @ViewById
    TextView userName;

    public UserView(Context context) {
        super(context);
    }

    @Override
    public void bind(User model) {
        userName.setText(model.getLogin());
    }
}
