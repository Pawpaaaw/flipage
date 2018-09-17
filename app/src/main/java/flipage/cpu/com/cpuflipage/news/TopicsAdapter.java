package flipage.cpu.com.cpuflipage.news;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import flipage.cpu.com.cpuflipage.R;
import flipage.cpu.com.cpuflipage.data.Topic;
import flipage.cpu.com.cpuflipage.forums.ForumsAdapter;

/**
 * Created by Jan Paolo Regalado on 6/27/18.
 * jan.regalado@safesat.com.ph
 * Sattelite GPS (GPS Tracking and Asset Management System)
 */
public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.TopicHolder> {


    private List<Topic> topicList;
    private View.OnClickListener clickListener;

    public TopicsAdapter(List<Topic> topicList, View.OnClickListener clickListener) {
        this.topicList = topicList;
        this.clickListener = clickListener;
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
        holder.textView.setText(topic.getTitle());
        holder.layout.setTag(topic);
        holder.layout.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    class TopicHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private View layout;

        public TopicHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.topic_text);
            layout = itemView;
        }
    }
}
