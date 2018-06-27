package flipage.cpu.com.cpuflipage.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;
import flipage.cpu.com.cpuflipage.BuildConfig;
import flipage.cpu.com.cpuflipage.R;
import flipage.cpu.com.cpuflipage.data.News;


public class NewsPageActivity extends AppCompatActivity implements DownloadFile.Listener {

    public static final String NEWS_DETAIL = "NEWS_DETAIL";
    public static News news;
    private RemotePDFViewPager pdfView;
    private PDFPagerAdapter adapter;
    private Button fullScreen;
    private boolean isFullScreen = false;

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
        StringBuilder builder = new StringBuilder();
        builder.append(BuildConfig.API)
                .append(news.getFilePath());
        Log.w("FILEPATH", builder.toString());

        pdfView = new RemotePDFViewPager(this, builder.toString(), this);
        fullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFullscreen(!isFullScreen);
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
    public void onSuccess(String url, String destinationPath) {
        Log.w("SUCCRESS", "success");
        adapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url));
        pdfView.setAdapter(adapter);
        LinearLayout layout = findViewById(R.id.pdf_ll);
        layout.addView(pdfView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.close();
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

    @Override
    public void onFailure(Exception e) {
        Log.w("FAILE", e.getMessage());
    }

    @Override
    public void onProgressUpdate(int progress, int total) {

    }
}
