package np.com.krishna.nightbeforeexam.models;

public class ProfilePicture {

    private Long id;

    private String fileName;

    private String fileType;

    private String uploadDir;

    private String uploadedBy;

    private String uploadedTime;

    private User user;

    public ProfilePicture(String fileName, String fileType, String uploadDir, String uploadedBy, String uploadedTime, User user) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.uploadDir = uploadDir;
        this.uploadedBy = uploadedBy;
        this.uploadedTime = uploadedTime;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getUploadedTime() {
        return uploadedTime;
    }

    public void setUploadedTime(String uploadedTime) {
        this.uploadedTime = uploadedTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
