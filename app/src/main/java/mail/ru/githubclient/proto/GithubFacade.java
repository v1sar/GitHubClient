package mail.ru.githubclient.proto;

import org.androidannotations.annotations.EBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

@EBean(scope = EBean.Scope.Singleton)
public class GithubFacade {

    public static final String API_URL = "https://api.github.com";

    public interface GitHub {
        @GET("/search/users?q={query}")
        Call<List<User>> users(@Path("query") String query);
    }

    public void getUsers(String name, Callback<List<User>> users) {
        //TODO
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
