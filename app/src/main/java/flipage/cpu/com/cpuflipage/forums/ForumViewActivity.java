package flipage.cpu.com.cpuflipage.forums;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import flipage.cpu.com.cpuflipage.R;
import flipage.cpu.com.cpuflipage.data.Post;
import flipage.cpu.com.cpuflipage.data.User;
import flipage.cpu.com.cpuflipage.profile.ProfileViewActivity;
import flipage.cpu.com.cpuflipage.utils.BitmapUtil;

/**
 * Created by Jan Paolo Regalado on 6/21/18.
 * jan.regalado@safesat.com.ph
 * Sattelite GPS (GPS Tracking and Asset Management System)
 */
public class ForumViewActivity extends AppCompatActivity{

    public static final String TOPIC_EXTRA = "POST_EXTRA";
    private Post post
            ;
    private RecyclerView recyclerView;
    public static User selectedUser;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        post = ForumsActivity.selected;
        TextView title = findViewById(R.id.title);
        TextView desc = findViewById(R.id.message);
        ImageView image = findViewById(R.id.image);
        recyclerView = findViewById(R.id.comment_list);
        fab = findViewById(R.id.fab1);

        if(post.getUser() != null && post.getUser().getDepartment() != null){
            desc.setText(post.getUser().getDepartment().getName());
        }

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
                builder.setView(view);
                builder.setTitle("Enter Comment");
                builder.setNegativeButton("Cancel",null);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        ForumCommentAdapter adapter = new ForumCommentAdapter(post.getComments(), this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedUser = (User) v.getTag();
                startActivity(new Intent(ForumViewActivity.this, ProfileViewActivity.class));
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
