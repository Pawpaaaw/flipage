package flipage.cpu.com.cpuflipage.data;

import android.os.Parcel;
import android.os.Parcelable;

public class CommentRequest implements Parcelable {

    private long newsId;
    private User user;
    private String message;

    public CommentRequest() {
    }

    protected CommentRequest(Parcel in) {
        newsId = in.readLong();
        user = in.readParcelable(User.class.getClassLoader());
        message = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(newsId);
        dest.writeParcelable(user, flags);
        dest.writeString(message);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CommentRequest> CREATOR = new Creator<CommentRequest>() {
        @Override
        public CommentRequest createFromParcel(Parcel in) {
            return new CommentRequest(in);
        }

        @Override
        public CommentRequest[] newArray(int size) {
            return new CommentRequest[size];
        }
    };

    public long getNewsId() {
        return newsId;
    }

    public void setNewsId(long newsId) {
        this.newsId = newsId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
