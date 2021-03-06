package flipage.cpu.com.cpuflipage.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import flipage.cpu.com.cpuflipage.R;
import flipage.cpu.com.cpuflipage.data.News;
import flipage.cpu.com.cpuflipage.data.Topic;
import flipage.cpu.com.cpuflipage.retrofit.RetrofitImplementation;

/**
 * Created by Jan Paolo Regalado on 6/27/18.
 * jan.regalado@safesat.com.ph
 * Sattelite GPS (GPS Tracking and Asset Management System)
 */
public class TopicsActivity extends AppCompatActivity {
    private News news;
    private RecyclerView recyclerView;
    private RetrofitImplementation implementation;
    public static Topic topicSelected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_list);
        news = NewsPageActivity.news;
        recyclerView = findViewById(R.id.list);

        TopicsAdapter adapter = new TopicsAdapter(news.getTopics(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topicSelected = (Topic) v.getTag();
                Intent intent = new Intent(TopicsActivity.this, TopicViewActivity.class);
                startActivity(intent);
            }
        });
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);


    }
}
