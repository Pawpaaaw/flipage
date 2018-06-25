package flipage.cpu.com.cpuflipage.forums;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import flipage.cpu.com.cpuflipage.R;
import flipage.cpu.com.cpuflipage.data.Topic;
import flipage.cpu.com.cpuflipage.retrofit.RetrofitImplementation;
import flipage.cpu.com.cpuflipage.utils.Callback;

/**
 * Created by Jan Paolo Regalado on 6/21/18.
 * jan.regalado@safesat.com.ph
 * Sattelite GPS (GPS Tracking and Asset Management System)
 */
public class ForumsActivity extends AppCompatActivity {

    private RetrofitImplementation implementation;
    private RecyclerView recyclerView;
    public static Topic selected;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_list);
        implementation = new RetrofitImplementation();
        recyclerView = findViewById(R.id.recycler);
        refreshLayout = findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                syncTopics();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        syncTopics();
    }

    private void syncTopics() {
        refreshLayout.setRefreshing(true);
        implementation.getAllTopic(new Callback() {
            @Override
            public void onSuccess(Object object) {
                refreshLayout.setRefreshing(false);
                List<Topic> topicList = (List<Topic>) object;

                if (topicList == null || topicList.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No posts available", Toast.LENGTH_SHORT).show();
                } else {
                    ForumsAdapter adapter = new ForumsAdapter(topicList, ForumsActivity.this, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Topic topic = (Topic) v.getTag();
                            selected = topic;
                            Intent intent = new Intent(ForumsActivity.this, ForumViewActivity.class);
                            startActivity(intent);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onError(String error) {
                refreshLayout.setRefreshing(false);
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
