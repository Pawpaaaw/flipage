package flipage.cpu.com.cpuflipage.news;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.necistudio.vigerpdf.VigerPDF;
import com.necistudio.vigerpdf.adapter.VigerAdapter;
import com.necistudio.vigerpdf.manage.OnResultListener;

import java.util.ArrayList;

import flipage.cpu.com.cpuflipage.BuildConfig;
import flipage.cpu.com.cpuflipage.R;
import flipage.cpu.com.cpuflipage.data.News;


public class NewsPageActivity extends AppCompatActivity {

    public static final String NEWS_DETAIL = "NEWS_DETAIL";
    public static News news;
    private VigerAdapter adapter;
    private Button fullScreen;
    private boolean isFullScreen = false;
    private VigerPDF vigerPDF;
    private ViewPager viewPager;
    private ArrayList<Bitmap> itemData = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flipage);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        news = NewsAdapter.newsSelected;
        fullScreen = findViewById(R.id.fullscren);
        viewPager = findViewById(R.id.viewPager);
        StringBuilder builder = new StringBuilder();
        builder.append(BuildConfig.API)
                .append("/uploaded-files/")
                .append(news.getFileName());
        Log.w("FILEPATH", builder.toString());
//
        fullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFullscreen(!isFullScreen);
            }
        });

        adapter = new VigerAdapter(getApplicationContext(), itemData);
        viewPager.setAdapter(adapter);

        vigerPDF = new VigerPDF(this);

        fromNetwork(builder.toString());
    }

    private void fromNetwork(String endpoint) {
        adapter.notifyDataSetChanged();
        vigerPDF.cancle();
        vigerPDF.initFromNetwork(endpoint, new OnResultListener() {
            @Override
            public void resultData(Bitmap data) {
                Log.e("data", "run");
                itemData.add(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void progressData(int progress) {
                Log.e("data", "" + progress);
            }

            @Override
            public void failed(Throwable t) {
                Log.e("PDF", t.getLocalizedMessage());
            }

        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
            case R.id.topics: {
                Intent intent = new Intent(NewsPageActivity.this, TopicsPage.class);
                startActivity(intent);
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_topics, menu);
        return true;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setFullscreen(boolean fullscreen) {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        if (fullscreen) {
            getSupportActionBar().hide();
            attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        } else {
            getSupportActionBar().show();
            attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        }
        getWindow().setAttributes(attrs);

        isFullScreen = !isFullScreen;
    }
}
