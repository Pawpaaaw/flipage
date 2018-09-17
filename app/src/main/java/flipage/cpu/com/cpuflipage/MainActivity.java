package flipage.cpu.com.cpuflipage;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import flipage.cpu.com.cpuflipage.data.News;
import flipage.cpu.com.cpuflipage.news.NewsAdapter;
import flipage.cpu.com.cpuflipage.premain.LoginActivity;
import flipage.cpu.com.cpuflipage.retrofit.RetrofitImplementation;
import flipage.cpu.com.cpuflipage.utils.Callback;
import flipage.cpu.com.cpuflipage.utils.FlipagePrefrences;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private NewsAdapter newsAdapter;
    private RetrofitImplementation implementation = new RetrofitImplementation();
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("News");
        swipe = findViewById(R.id.swipe);
        swipe.setEnabled(false);
        swipe.setOnRefreshListener(this);
        recyclerView = findViewById(R.id.gridview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        enableBackButton();

    }

    private void enableBackButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }


    private void syncNews() {
        swipe.setRefreshing(true);
        implementation.getNewsByDeptId(DepartmentActivity.selectedDepartment.getId(),new Callback() {
            @Override
            public void onSuccess(Object object) {
                swipe.setRefreshing(false);
                List<News> news = (List<News>) object;
                newsAdapter = new NewsAdapter(news, MainActivity.this);
                recyclerView.setAdapter(newsAdapter);
            }

            @Override
            public void onError(String error) {
                swipe.setRefreshing(false);
                Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        syncNews();
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }


    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }

    @Override
    public void onBackPressed() {
        if (FlipagePrefrences.getIsLoggedIn()) {
            super.onBackPressed();
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case android.R.id.home: {
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onRefresh() {
        syncNews();
    }
}
