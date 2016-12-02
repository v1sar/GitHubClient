package mail.ru.githubclient.proto;

import android.util.Log;
import android.widget.Toast;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;

import java.io.IOException;
import java.util.List;

import mail.ru.githubclient.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

@EBean(scope = EBean.Scope.Singleton)
public class GithubFacade {

    private static final String API_URL = "https://api.github.com";
    private static String oAuth;
    private static User loggedUser;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public interface GitHub {
        @GET("/search/users")
        Call<UsersResult> users(@Query("q") String query);
        @GET("user")
        Call<User> getOAuth(@Header("Authorization") String authorization);
    }

    private GitHub github = retrofit.create(GitHub.class);

    @Background
    public void getUsers(String name, Callback<List<User>> users) {
        Call<UsersResult> call = github.users(name);

        try {
            UsersResult res = call.execute().body();
            Log.d("Facade", res + "");
            List<User> us = res.getItems();

            users.onSuccess(us);
        }
        catch(IOException e) {
            users.onError(e);
        }
    }

    @Background
    public void getOAuth(String base64, final Callback<User> user) {
        oAuth = "Basic "+base64.trim();
        Call call = github.getOAuth(oAuth);
        call.enqueue(new retrofit2.Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.errorBody() == null) {
                    user.onSuccess((User) response.body());
                    loggedUser = (User) response.body();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("Facade", t.getMessage());
            }
        });
    }

    public void getRepos(User user, Callback<List<Repo>> repos) {
        //TODO
    }

    public void getGists(User user, Callback<List<Gist>> gists) {
        //TODO
    }

    public interface Callback<T> {
        void onSuccess(T result);

        void onError(Exception e);
    }

}
