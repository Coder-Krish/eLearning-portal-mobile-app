package np.com.krishna.nightbeforeexam.models;

public class DiscussionForum {
    private Long id;

    private String content;

    private String fileName;

    private String fileType;

    private String postedBy;

    private String postedAt;

    private String uploadDir;
    private Subjects subjects;

    private User user;

    private boolean active;

    private Programs programOfDiscussion;

    public DiscussionForum(String content, String fileName, String fileType, String postedBy, String postedAt, String uploadDir, Subjects subjects, User user, boolean active, Programs programOfDiscussion) {
        this.content = content;
        this.fileName = fileName;
        this.fileType = fileType;
        this.postedBy = postedBy;
        this.postedAt = postedAt;
        this.uploadDir = uploadDir;
        this.subjects = subjects;
        this.user = user;
        this.active = active;
        this.programOfDiscussion = programOfDiscussion;
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

    public String getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(String postedAt) {
        this.postedAt = postedAt;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public Subjects getSubjects() {
        return subjects;
    }

    public void setSubjects(Subjects subjects) {
        this.subjects = subjects;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Programs getProgramOfDiscussion() {
        return programOfDiscussion;
    }

    public void setProgramOfDiscussion(Programs programOfDiscussion) {
        this.programOfDiscussion = programOfDiscussion;
    }
}
