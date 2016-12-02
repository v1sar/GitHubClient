package mail.ru.githubclient;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Base64;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import mail.ru.githubclient.proto.GithubFacade;
import mail.ru.githubclient.proto.User;
import mail.ru.githubclient.reflect.Proxies;
import mail.ru.githubclient.reflect.Wrapper;

@EActivity(R.layout.activity_main)
@OptionsMenu({R.menu.menu_main})
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

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    private GithubFacade.Callback<List<User>> getUsersCallback;
    private GithubFacade.Callback<User> getOAuthCallback;

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

        GithubFacade.Callback<User> callbackOAuth = new GithubFacade.Callback<User>() {
            @Override
            public void onSuccess(User result) {
                Toast.makeText(MainActivity.this, "Hello "+result.getLogin(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {

            }
        };

        Wrapper<GithubFacade.Callback> wrapper = Proxies.wrap(GithubFacade.Callback.class, callback);
        wrappers.add(wrapper);
        getUsersCallback = wrapper.delegate();

        Wrapper<GithubFacade.Callback> wrapper1 = Proxies.wrap(GithubFacade.Callback.class, callbackOAuth);
        wrappers.add(wrapper1);
        getOAuthCallback = wrapper1.delegate();
    }

    @AfterViews
    void initViews() {
        // Setup adapter here
        users.setLayoutManager(new LinearLayoutManager(this));
        users.setAdapter(adapter);
        setSupportActionBar(toolbar);
    }


    @Click(R.id.search)
    void onSearchClick() {
        // Perform search
        facade.getUsers(searchName.getText().toString(), getUsersCallback);
    }

    @OptionsItem
    void actionLogin() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Login to github");
        alert.setMessage("Provide your login and password");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText inputLogin = new EditText(this);
        inputLogin.setHint("Login");
        final EditText inputPassword = new EditText(this);
        inputPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        inputPassword.setHint("Password");
        layout.addView(inputLogin);
        layout.addView(inputPassword);
        alert.setView(layout);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String userPass = inputLogin.getText()+":"+inputPassword.getText();
                byte[] data = null;
                try {
                    data = userPass.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                facade.getOAuth(Base64.encodeToString(data, Base64.DEFAULT), getOAuthCallback);
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alert.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (Wrapper<?> wrapper : wrappers) {
            wrapper.unregister();
        }
    }
}
