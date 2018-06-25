package flipage.cpu.com.cpuflipage.forums;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import flipage.cpu.com.cpuflipage.R;
import flipage.cpu.com.cpuflipage.data.Comment;
import flipage.cpu.com.cpuflipage.utils.BitmapUtil;

/**
 * Created by Jan Paolo Regalado on 6/21/18.
 * jan.regalado@safesat.com.ph
 * Sattelite GPS (GPS Tracking and Asset Management System)
 */
public class ForumCommentAdapter extends RecyclerView.Adapter<ForumCommentAdapter.ForumCommentHolder> {

    private List<Comment> comments;
    private Activity activity;
    private View.OnClickListener listener;

    public ForumCommentAdapter(List<Comment> comments, Activity activity, View.OnClickListener listener) {
        this.comments = comments;
        this.activity = activity;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ForumCommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_row, parent, false);
        ForumCommentHolder adapter = new ForumCommentHolder(layout);
        return adapter;
    }

    @Override
    public void onBindViewHolder(@NonNull ForumCommentHolder holder, int position) {
        Comment comment = comments.get(position);
        new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapUtil.decodeBase64(comment.getUser().getImage());
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
        holder.imageView.setTag(comment.getUser());
        holder.imageView.setOnClickListener(listener);
        holder.title.setText(comment.getUser().getUsername());
        holder.message.setText(comment.getMessage());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ForumCommentHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title;
        TextView message;

        public ForumCommentHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image);
            title = view.findViewById(R.id.title);
            message = view.findViewById(R.id.message);
        }
    }
}
