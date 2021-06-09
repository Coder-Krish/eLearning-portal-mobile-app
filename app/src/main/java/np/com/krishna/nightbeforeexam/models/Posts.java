package np.com.krishna.nightbeforeexam.models;

public class Posts {
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
}
