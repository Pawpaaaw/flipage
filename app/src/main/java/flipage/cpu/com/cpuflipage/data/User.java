package flipage.cpu.com.cpuflipage.data;

import android.os.Parcel;
import android.os.Parcelable;


public class User implements Parcelable {

    private long id;
    private long version;
    private boolean admin;
    private String email;
    private String username;
    private String image;
    private String password;
    private String idNumber;
    private String department;

    public User() {
    }


    protected User(Parcel in) {
        id = in.readLong();
        version = in.readLong();
        admin = in.readByte() != 0;
        email = in.readString();
        username = in.readString();
        image = in.readString();
        password = in.readString();
        idNumber = in.readString();
        department = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(version);
        dest.writeByte((byte) (admin ? 1 : 0));
        dest.writeString(email);
        dest.writeString(username);
        dest.writeString(image);
        dest.writeString(password);
        dest.writeString(idNumber);
        dest.writeString(department);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
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

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        admin = admin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}
