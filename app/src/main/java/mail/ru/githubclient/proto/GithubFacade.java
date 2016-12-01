package mail.ru.githubclient.proto;

import org.androidannotations.annotations.EBean;

import java.util.List;

@EBean(scope = EBean.Scope.Singleton)
public class GithubFacade {

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
