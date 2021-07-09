package np.com.krishna.nightbeforeexam.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Semesters implements Parcelable {
    private Long id;
    private String semesterName;

    public Semesters(Long id, String semesterName) {
        this.id = id;
        this.semesterName = semesterName;
    }

    protected Semesters(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        semesterName = in.readString();
    }

    public static final Creator<Semesters> CREATOR = new Creator<Semesters>() {
        @Override
        public Semesters createFromParcel(Parcel in) {
            return new Semesters(in);
        }

        @Override
        public Semesters[] newArray(int size) {
            return new Semesters[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
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
        dest.writeString(semesterName);
    }
}
