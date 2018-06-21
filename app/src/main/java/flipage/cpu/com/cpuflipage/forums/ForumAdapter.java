package flipage.cpu.com.cpuflipage.forums;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import flipage.cpu.com.cpuflipage.data.Comment;

/**
 * Created by Jan Paolo Regalado on 6/21/18.
 * jan.regalado@safesat.com.ph
 * Sattelite GPS (GPS Tracking and Asset Management System)
 */
public class ForumAdapter extends BaseAdapter {

    private List<Comment> commentList;

    public ForumAdapter(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public int getCount() {
        if(commentList == null){
            return 0;
        }
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        if(commentList == null){
            return null;
        }
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
