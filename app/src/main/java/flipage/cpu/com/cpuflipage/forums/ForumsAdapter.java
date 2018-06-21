package flipage.cpu.com.cpuflipage.forums;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;

import java.util.List;

import flipage.cpu.com.cpuflipage.R;
import flipage.cpu.com.cpuflipage.data.Topic;
import flipage.cpu.com.cpuflipage.news.NewsAdapter;
import flipage.cpu.com.cpuflipage.utils.BitmapUtil;

/**
 * Created by Jan Paolo Regalado on 6/21/18.
 * jan.regalado@safesat.com.ph
 * Sattelite GPS (GPS Tracking and Asset Management System)
 */
public class ForumsAdapter extends RecyclerView.Adapter<ForumsAdapter.TopicHolder> {

    private List<Topic> topicList;
    private Activity activity;
    private View.OnClickListener listener;

    public ForumsAdapter(List<Topic> topicList, Activity activity, View.OnClickListener listener) {
        this.topicList = topicList;
        this.activity = activity;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TopicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_row_layout, parent, false);
        TopicHolder adapter = new TopicHolder(layout);
        return adapter;
    }

    @Override
    public void onBindViewHolder(@NonNull TopicHolder holder, int position) {
        Topic topic = topicList.get(position);
        new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapUtil.decodeBase64(topic.getUser().getImage());
                if (bitmap != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            holder.imageView.setImageBitmap(bitmap);
                        }
                    });
                }

            }
        }.start();
        holder.title.setText(topic.getTitle());
        holder.message.setText(topic.getDescription());
        holder.layout.setTag(topic);
        holder.layout.setOnClickListener(listener);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class TopicHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        TextView message;
        CardView layout;

        public TopicHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image);
            title = view.findViewById(R.id.title);
            message = view.findViewById(R.id.message);
            layout = view.findViewById(R.id.card);
        }
    }
}

