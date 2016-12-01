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

import java.util.ArrayList;
import java.util.List;

import mail.ru.githubclient.proto.GithubFacade;
import mail.ru.githubclient.proto.User;
import mail.ru.githubclient.reflect.Proxies;
import mail.ru.githubclient.reflect.Wrapper;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private final List<Wrapper<?>> wrappers = new ArrayList<>();

    @Bean
    GithubFacade facade;

    @Bean
    UsersAdapter adapter;

    @ViewById(R.id.user_name)
    EditText searchName;

    @ViewById(R.id.users)
    RecyclerView users;

    private GithubFacade.Callback<List<User>> getUsersCallback;

    @AfterViews
    void initCallbacks() {
        GithubFacade.Callback<List<User>> callback = new GithubFacade.Callback<List<User>>() {
            @Override
            public void onSuccess(List<User> result) {
                adapter.setUsers(result);
            }

            @Override
            public void onError(Exception e) {
                //TODO
            }
        };

        Wrapper<GithubFacade.Callback> wrapper = Proxies.wrap(GithubFacade.Callback.class, callback);
        wrappers.add(wrapper);
        getUsersCallback = wrapper.delegate();
    }

    @AfterViews
    void initViews() {
        // Setup adapter here
        users.setLayoutManager(new LinearLayoutManager(this));
        users.setAdapter(adapter);
    }

    @Click(R.id.search)
    void onSearchClick() {
        // Perform search

        facade.getUsers(searchName.getText().toString(), getUsersCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (Wrapper<?> wrapper : wrappers) {
            wrapper.unregister();
        }
    }
}
