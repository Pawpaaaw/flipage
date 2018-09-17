package flipage.cpu.com.cpuflipage.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Jan Paolo Regalado on 6/21/18.
 * jan.regalado@safesat.com.ph
 * Sattelite GPS (GPS Tracking and Asset Management System)
 */
public class Post implements Parcelable{
    private Long id;
    private String title;
    private User user;
    private List<Comment> comments;
    private String description;

    public Post() {
    }

    protected Post(Parcel in) {
        description = in.readString();
        id = in.readLong();
        title = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
        comments = in.createTypedArrayList(Comment.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeParcelable(user, flags);
        dest.writeTypedList(comments);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
