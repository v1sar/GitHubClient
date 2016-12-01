package mail.ru.githubclient;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import mail.ru.githubclient.proto.GithubFacade;
import mail.ru.githubclient.proto.User;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewById(R.id.user_name)
    EditText searchName;

    @ViewById(R.id.users)
    RecyclerView users;

    @Bean(GithubFacade.class)
    GithubFacade ghFacade;
    @Bean
    UsersAdapter adapter;

    @AfterViews
    void initViews() {
        users.setLayoutManager(new LinearLayoutManager(this));
        users.setAdapter(adapter);
    }

    @Click(R.id.search)
    void onSearchClick() {
        // Perform search
        ghFacade.getUsers(searchName.getText().toString(), new GithubFacade.Callback<List<User>>() {
            @Override
            public void onSuccess(List<User> result) {
                for (User u : result) {
                    Log.d("USERS", u.getLogin());
                }
            }

            @Override
            public void onError(Exception e) {
                Log.d("USERS", "error");
            }
        });
    }
}
