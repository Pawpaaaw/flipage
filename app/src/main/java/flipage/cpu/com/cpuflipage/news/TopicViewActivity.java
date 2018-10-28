package flipage.cpu.com.cpuflipage.news;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import flipage.cpu.com.cpuflipage.R;
import flipage.cpu.com.cpuflipage.data.Comment;
import flipage.cpu.com.cpuflipage.data.News;
import flipage.cpu.com.cpuflipage.data.Topic;
import flipage.cpu.com.cpuflipage.data.User;
import flipage.cpu.com.cpuflipage.forums.ForumCommentAdapter;
import flipage.cpu.com.cpuflipage.profile.ProfileViewActivity;
import flipage.cpu.com.cpuflipage.retrofit.RetrofitImplementation;
import flipage.cpu.com.cpuflipage.utils.Callback;
import flipage.cpu.com.cpuflipage.utils.FlipagePrefrences;

/**
 * Created by Jan Paolo Regalado on 6/27/18.
 * jan.regalado@safesat.com.ph
 * Sattelite GPS (GPS Tracking and Asset Management System)
 */
public class TopicViewActivity extends AppCompatActivity{

    public static final String TOPIC_EXTRA = "POST_EXTRA";
    private Topic topic;
    private News news;
    private RecyclerView recyclerView;
    public static User selectedUser;
    private FloatingActionButton fab;
    private RetrofitImplementation retrofit = new RetrofitImplementation();
    private ForumCommentAdapter adapter;
    private LinearLayout progress;
    private RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_view_layout);
        this.topic = TopicsActivity.topicSelected;
        this.news = NewsPageActivity.news;
        TextView title = findViewById(R.id.title);
        TextView desc = findViewById(R.id.description);
        desc.setText(topic.getDescription());
        recyclerView = findViewById(R.id.comment_list);
        fab = findViewById(R.id.fab1);
        progress = findViewById(R.id.progress_ll);

        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        title.setText(topic.getTitle());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FlipagePrefrences.getIsGuest()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TopicViewActivity.this);
                    View view = getLayoutInflater().inflate(R.layout.layout_edittext, null);
                    EditText et = view.findViewById(R.id.message);
                    view.findViewById(R.id.description).setVisibility(View.GONE);
                    builder.setView(view);
                    builder.setTitle("Enter Comment");
                    builder.setNegativeButton("Cancel", null);
                    builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            String message = et.getText().toString().trim();

                            Comment comment = new Comment();
                            comment.setMessage(message);
                            comment.setUser(FlipagePrefrences.getUser());
                            comment.setArticleId(topic.getId());
                            progress.setVisibility(View.VISIBLE);
                            retrofit.addCommentToTopic(comment, new Callback() {
                                @Override
                                public void onSuccess(Object object) {
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    progress.setVisibility(View.GONE);
                                }

                                @Override
                                public void onError(String error) {
                                    Toast.makeText(TopicViewActivity.this, error, Toast.LENGTH_SHORT).show();
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    progress.setVisibility(View.GONE);
                                }
                            });

                        }
                    });

                    builder.show();
                }else {
                    Toast.makeText(TopicViewActivity.this, "Please login to add comment", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter = new ForumCommentAdapter(topic.getComments(), this, clickListener());
        recyclerView.setAdapter(adapter);
    }

    @NonNull
    private View.OnClickListener clickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedUser = (User) v.getTag();
                startActivity(new Intent(TopicViewActivity.this, ProfileViewActivity.class));
            }
        };
    }
}
