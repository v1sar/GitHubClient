package mail.ru.githubclient.proto;

import org.androidannotations.annotations.EBean;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

@EBean(scope = EBean.Scope.Singleton)
public class GithubFacade {

    private static final String API_URL = "https://api.github.com";

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public interface GitHub {
        @GET("/search/users?q={query}")
        Call<List<User>> users(@Path("query") String query);
    }

    GitHub github = retrofit.create(GitHub.class);

    public void getUsers(String name, Callback<List<User>> users) {
        Call<List<User>> call = github.users(name);

        try {
            List<User> us = call.execute().body();
            users.onSuccess(us);
        }
        catch(IOException e) {
            users.onError(e);
        }
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
