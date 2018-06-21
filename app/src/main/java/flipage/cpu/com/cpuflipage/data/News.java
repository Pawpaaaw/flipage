package flipage.cpu.com.cpuflipage.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paolo on 5/1/18.
 */

public class News implements Parcelable {

    private long id;
    private long version;
    private boolean active;
    private long dateCreated;
    private String title;
    private String filePath;
    private Department department;
    private List<Topic> topics;

    public News() {
    }


    protected News(Parcel in) {
        id = in.readLong();
        version = in.readLong();
        active = in.readByte() != 0;
        dateCreated = in.readLong();
        title = in.readString();
        filePath = in.readString();
        department = in.readParcelable(Department.class.getClassLoader());
        topics = in.createTypedArrayList(Topic.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(version);
        dest.writeByte((byte) (active ? 1 : 0));
        dest.writeLong(dateCreated);
        dest.writeString(title);
        dest.writeString(filePath);
        dest.writeParcelable(department, flags);
        dest.writeTypedList(topics);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
