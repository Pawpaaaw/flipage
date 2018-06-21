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
    private String title;
    private Department department;
    private List<Comment> comments;


    protected Post(Parcel in) {
        title = in.readString();
        department = in.readParcelable(Department.class.getClassLoader());
        comments = in.createTypedArrayList(Comment.CREATOR);
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeParcelable(department, flags);
        dest.writeTypedList(comments);
    }
}
