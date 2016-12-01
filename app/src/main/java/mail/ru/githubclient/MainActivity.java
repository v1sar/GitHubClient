package mail.ru.githubclient;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewById(R.id.user_name)
    EditText searchName;

    @ViewById(R.id.users)
    RecyclerView users;

    @AfterViews
    void initViews() {
        // Setup adapter here
    }

    @Click(R.id.search)
    void onSearchClick() {
        // Perform search
    }
}
