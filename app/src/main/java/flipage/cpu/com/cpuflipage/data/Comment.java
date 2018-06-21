package flipage.cpu.com.cpuflipage.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by paolo on 5/1/18.
 */

public class Comment implements Parcelable{


    private String message;
    private User user;

    public Comment() {
    }

    protected Comment(Parcel in) {
        message = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    public String getComment() {
        return message;
    }

    public void setComment(String comment) {
        this.message = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(message);
        parcel.writeParcelable(user, i);
    }
}
