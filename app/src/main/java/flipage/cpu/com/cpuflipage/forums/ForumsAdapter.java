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
import android.widget.TextView;

import java.util.List;

import flipage.cpu.com.cpuflipage.R;
import flipage.cpu.com.cpuflipage.data.Post;
import flipage.cpu.com.cpuflipage.utils.BitmapUtil;

/**
 * Created by Jan Paolo Regalado on 6/21/18.
 * jan.regalado@safesat.com.ph
 * Sattelite GPS (GPS Tracking and Asset Management System)
 */
public class ForumsAdapter extends RecyclerView.Adapter<ForumsAdapter.PostHolder> {

    private List<Post> postList;
    private Activity activity;
    private View.OnClickListener listener;

    public ForumsAdapter(List<Post> postList, Activity activity, View.OnClickListener listener) {
        this.postList = postList;
        this.activity = activity;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row_layout, parent, false);
        PostHolder adapter = new PostHolder(layout);
        return adapter;
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        Post topic = postList.get(position);
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
        if(topic.getUser() != null && topic.getUser().getDepartment() != null) {
            holder.message.setText(topic.getUser().getDepartment().getName());
        }
        holder.layout.setTag(topic);
        holder.layout.setOnClickListener(listener);

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    class PostHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        TextView message;
        CardView layout;

        public PostHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image);
            title = view.findViewById(R.id.title);
            message = view.findViewById(R.id.message);
            layout = view.findViewById(R.id.card);
        }
    }
}

