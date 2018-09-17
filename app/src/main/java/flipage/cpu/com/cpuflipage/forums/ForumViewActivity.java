package flipage.cpu.com.cpuflipage.forums;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import flipage.cpu.com.cpuflipage.R;
import flipage.cpu.com.cpuflipage.data.Comment;
import flipage.cpu.com.cpuflipage.data.Post;
import flipage.cpu.com.cpuflipage.data.User;
import flipage.cpu.com.cpuflipage.profile.ProfileViewActivity;
import flipage.cpu.com.cpuflipage.retrofit.RetrofitImplementation;
import flipage.cpu.com.cpuflipage.utils.BitmapUtil;
import flipage.cpu.com.cpuflipage.utils.Callback;
import flipage.cpu.com.cpuflipage.utils.FlipagePrefrences;

/**
 * Created by Jan Paolo Regalado on 6/21/18.
 * jan.regalado@safesat.com.ph
 * Sattelite GPS (GPS Tracking and Asset Management System)
 */
public class ForumViewActivity extends AppCompatActivity{

    public static final String TOPIC_EXTRA = "POST_EXTRA";
    private Post post;
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
        setContentView(R.layout.activity_forum);
        post = ForumsActivity.selected;
        TextView title = findViewById(R.id.title);
        TextView desc = findViewById(R.id.message);
        TextView descr = findViewById(R.id.description);
        ImageView image = findViewById(R.id.image);
        recyclerView = findViewById(R.id.comment_list);
        fab = findViewById(R.id.fab1);
        progress = findViewById(R.id.progress_ll);
        enableBackButton();

        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        if(post.getUser() != null && post.getUser().getDepartment() != null){
            desc.setText(post.getUser().getDepartment().getName());
        }
        descr.setText(post.getDescription());
        title.setText(post.getTitle());
        new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapUtil.decodeBase64(post.getUser().getImage());
                if (bitmap != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            image.setImageBitmap(bitmap);
                        }
                    });
                }

            }
        }.start();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ForumViewActivity.this);
                View view = getLayoutInflater().inflate(R.layout.layout_edittext, null);
                EditText et = view.findViewById(R.id.message);
                builder.setView(view);
                builder.setTitle("Enter Comment");
                builder.setNegativeButton("Cancel",null);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        String message = et.getText().toString().trim();

                        Comment comment = new Comment();
                        comment.setMessage(message);
                        comment.setUser(FlipagePrefrences.getUser());
                        comment.setArticleId(post.getId());
                        progress.setVisibility(View.VISIBLE);
                        retrofit.addCommentToPost(comment, new Callback() {
                            @Override
                            public void onSuccess(Object object) {
                                ForumViewActivity.this.post = (Post) object;
                                adapter = new ForumCommentAdapter(post.getComments(), ForumViewActivity.this, clickListener());
                                recyclerView.setAdapter(adapter);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                progress.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(String error) {
                                Toast.makeText(ForumViewActivity.this,error, Toast.LENGTH_SHORT).show();
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                progress.setVisibility(View.GONE);
                            }
                        });

                    }
                });

                builder.show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter = new ForumCommentAdapter(post.getComments(), this, clickListener());
        recyclerView.setAdapter(adapter);
    }

    @NonNull
    private View.OnClickListener clickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedUser = (User) v.getTag();
                startActivity(new Intent(ForumViewActivity.this, ProfileViewActivity.class));
            }
        };
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
