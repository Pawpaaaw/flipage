package flipage.cpu.com.cpuflipage.forums;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import flipage.cpu.com.cpuflipage.R;
import flipage.cpu.com.cpuflipage.data.Post;
import flipage.cpu.com.cpuflipage.news.TopicViewActivity;
import flipage.cpu.com.cpuflipage.retrofit.RetrofitImplementation;
import flipage.cpu.com.cpuflipage.utils.Callback;
import flipage.cpu.com.cpuflipage.utils.FlipagePrefrences;

/**
 * Created by Jan Paolo Regalado on 6/21/18.
 * jan.regalado@safesat.com.ph
 * Sattelite GPS (GPS Tracking and Asset Management System)
 */
public class ForumsActivity extends AppCompatActivity {

    private RetrofitImplementation implementation;
    private RecyclerView recyclerView;
    public static Post selected;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView.LayoutManager manager;
    private FloatingActionButton fab;
    private LinearLayout progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_list);
        implementation = new RetrofitImplementation();
        recyclerView = findViewById(R.id.recycler);
        refreshLayout = findViewById(R.id.refresh);
        fab = findViewById(R.id.fab1);
        progress = findViewById(R.id.progress_ll);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        enableBackButton();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FlipagePrefrences.getIsGuest()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ForumsActivity.this);
                    View view = getLayoutInflater().inflate(R.layout.layout_edittext, null);
                    EditText et = view.findViewById(R.id.message);
                    EditText desc = view.findViewById(R.id.description);
                    builder.setNegativeButton("Cancel", null);
                    builder.setTitle("Add post to forum");
                    builder.setView(view);
                    builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String message = et.getText().toString().trim();
                            String descr = desc.getText().toString().trim();
                            if (message.isEmpty()) {
                                et.setError("Field is required");
                            } else {
                                progress.setVisibility(View.VISIBLE);
                                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                Post post = new Post();
                                post.setDescription(descr);
                                post.setTitle(message);
                                post.setUser(FlipagePrefrences.getUser());
                                implementation.createPost(post, new Callback() {
                                    @Override
                                    public void onSuccess(Object object) {
                                        progress.setVisibility(View.GONE);
                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        Toast.makeText(ForumsActivity.this, "Post in forum created", Toast.LENGTH_SHORT).show();
                                        syncPosts();
                                    }

                                    @Override
                                    public void onError(String error) {
                                        progress.setVisibility(View.GONE);
                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        Toast.makeText(ForumsActivity.this, error, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                    builder.show();
                } else
                    Toast.makeText(ForumsActivity.this, "Please login to add comment", Toast.LENGTH_SHORT).show();
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                syncPosts();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        syncPosts();
    }

    private void syncPosts() {
        refreshLayout.setRefreshing(true);
        implementation.getAllPosts(new Callback() {
            @Override
            public void onSuccess(Object object) {
                refreshLayout.setRefreshing(false);
                List<Post> topicList = (List<Post>) object;

                if (topicList == null) {
                    Toast.makeText(getApplicationContext(), "No posts available", Toast.LENGTH_SHORT).show();
                } else {
                    ForumsAdapter adapter = new ForumsAdapter(topicList, ForumsActivity.this, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Post topic = (Post) v.getTag();
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

    private void enableBackButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
