package flipage.cpu.com.cpuflipage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.github.dewinjm.monthyearpicker.MonthYearPickerDialog;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;
import com.hlab.fabrevealmenu.listeners.OnFABMenuSelectedListener;
import com.hlab.fabrevealmenu.view.FABRevealMenu;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import flipage.cpu.com.cpuflipage.data.News;
import flipage.cpu.com.cpuflipage.data.User;
import flipage.cpu.com.cpuflipage.forums.ForumsActivity;
import flipage.cpu.com.cpuflipage.news.NewsAdapter;
import flipage.cpu.com.cpuflipage.premain.LoginActivity;
import flipage.cpu.com.cpuflipage.premain.WelcomeActivity;
import flipage.cpu.com.cpuflipage.profile.ProfileActivity;
import flipage.cpu.com.cpuflipage.retrofit.RetrofitImplementation;
import flipage.cpu.com.cpuflipage.utils.Callback;
import flipage.cpu.com.cpuflipage.utils.FlipagePrefrences;

public class MainActivity extends AppCompatActivity implements OnFABMenuSelectedListener {

    private FABRevealMenu fabRevealMenu;
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
        recyclerView = findViewById(R.id.gridview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));


        final FloatingActionButton fab = findViewById(R.id.fab);
        fabRevealMenu = findViewById(R.id.fabMenu);
        if (FlipagePrefrences.getIsAdmin())
            fabRevealMenu.setMenu(R.menu.menu_fab_admin);
        fabRevealMenu.bindAnchorView(fab);
        fabRevealMenu.setOnFABMenuSelectedListener(this);

    }


    private void syncNews() {
        swipe.setRefreshing(true);
        implementation.getNews(new Callback() {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
            case R.id.logout: {
                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                FlipagePrefrences.setUser(new User(), false);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.forums:{
                Intent intent = new Intent(MainActivity.this, ForumsActivity.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMenuItemSelected(View view, int id) {
        switch (id) {
            case R.id.profile: {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.search: {

                Calendar calendar = Calendar.getInstance();
                MonthYearPickerDialogFragment dialogFragment = MonthYearPickerDialogFragment
                        .getInstance(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
                dialogFragment.setOnDateSetListener(new MonthYearPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int year, int month) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        newsAdapter.getFilter().filter(String.valueOf(calendar.getTimeInMillis()));
                        SimpleDateFormat format = new SimpleDateFormat("MMMM-yyyy");
                        MainActivity.this.setTitle(format.format(calendar.getTime()));
                        fabRevealMenu.bringToFront();
                    }
                });
                dialogFragment.show(getSupportFragmentManager(), null);
                View view1 = this.getCurrentFocus();
                if (view1 != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
                }
                break;
            }
            case R.id.clear:{
                if(newsAdapter != null) {
                    newsAdapter.getFilter().filter(String.valueOf(0));
                    setTitle("News");
                }
                break;
            }
            case R.id.refresh: {
                syncNews();
                break;
            }
            default:
                break;
        }
    }
}
