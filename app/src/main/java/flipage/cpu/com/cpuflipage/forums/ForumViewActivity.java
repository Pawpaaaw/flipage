package flipage.cpu.com.cpuflipage.forums;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import flipage.cpu.com.cpuflipage.R;
import flipage.cpu.com.cpuflipage.data.Topic;
import flipage.cpu.com.cpuflipage.utils.BitmapUtil;

/**
 * Created by Jan Paolo Regalado on 6/21/18.
 * jan.regalado@safesat.com.ph
 * Sattelite GPS (GPS Tracking and Asset Management System)
 */
public class ForumViewActivity extends AppCompatActivity{

    public static final String TOPIC_EXTRA = "POST_EXTRA";
    private Topic topic;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        topic = ForumsActivity.selected;
        TextView title = findViewById(R.id.title);
        TextView desc = findViewById(R.id.message);
        ImageView image = findViewById(R.id.image);
        recyclerView = findViewById(R.id.comment_list);

        title.setText(topic.getTitle());
        desc.setText(topic.getDescription());
        new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapUtil.decodeBase64(topic.getUser().getImage());
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

    }

    @Override
    protected void onResume() {
        super.onResume();
        ForumCommentAdapter adapter = new ForumCommentAdapter(topic.getComments(), this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo create profile page for viewing
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
