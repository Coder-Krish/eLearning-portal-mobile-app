package np.com.krishna.nightbeforeexam.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Posts implements Parcelable {
    private Long id;

    private String content;

    private String fileName;

    private String fileType;

    private String postedBy;

    private String postedTime;

    private String uploadDir;

    private boolean active;

    private User user;

    public Posts(String content, String fileName, String fileType, String postedBy, String postedTime, String uploadDir, boolean active,User user) {
        this.content = content;
        this.fileName = fileName;
        this.fileType = fileType;
        this.postedBy = postedBy;
        this.postedTime = postedTime;
        this.uploadDir = uploadDir;
        this.active = active;
        this.user = user;
    }

    public Posts() {

    }

    protected Posts(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        content = in.readString();
        fileName = in.readString();
        fileType = in.readString();
        postedBy = in.readString();
        postedTime = in.readString();
        uploadDir = in.readString();
        active = in.readByte() != 0;
        user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<Posts> CREATOR = new Creator<Posts>() {
        @Override
        public Posts createFromParcel(Parcel in) {
            return new Posts(in);
        }

        @Override
        public Posts[] newArray(int size) {
            return new Posts[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(String postedTime) {
        this.postedTime = postedTime;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(content);
        dest.writeString(fileName);
        dest.writeString(fileType);
        dest.writeString(postedBy);
        dest.writeString(postedTime);
        dest.writeString(uploadDir);
        dest.writeByte((byte) (active ? 1 : 0));
        dest.writeParcelable(user, flags);
    }
}
