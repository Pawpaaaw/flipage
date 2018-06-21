package flipage.cpu.com.cpuflipage.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Jan Paolo Regalado on 6/21/18.
 * jan.regalado@safesat.com.ph
 * Sattelite GPS (GPS Tracking and Asset Management System)
 */
public class Forum implements Parcelable{
    private User user;
    private List<Comment> comment;


    protected Forum(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
        comment = in.createTypedArrayList(Comment.CREATOR);
    }

    public static final Creator<Forum> CREATOR = new Creator<Forum>() {
        @Override
        public Forum createFromParcel(Parcel in) {
            return new Forum(in);
        }

        @Override
        public Forum[] newArray(int size) {
            return new Forum[size];
        }
    };

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
        dest.writeTypedList(comment);
    }
}
