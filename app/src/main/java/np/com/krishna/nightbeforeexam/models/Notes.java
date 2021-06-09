package np.com.krishna.nightbeforeexam.models;

public class Notes {
    private Long id;

    private String program;

    private String semester;

    private String subject;

    private String fileName;

    private String fileType;

    private String addedBy;

    private String addedTime;

    private Subjects subjects;


    private String uploadDir;

    private boolean active;

    public Notes(String program, String semester, String subject, String fileName, String fileType, String addedBy, String addedTime, Subjects subjects, String uploadDir, boolean active) {
        this.program = program;
        this.semester = semester;
        this.subject = subject;
        this.fileName = fileName;
        this.fileType = fileType;
        this.addedBy = addedBy;
        this.addedTime = addedTime;
        this.subjects = subjects;
        this.uploadDir = uploadDir;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(String addedTime) {
        this.addedTime = addedTime;
    }

    public Subjects getSubjects() {
        return subjects;
    }

    public void setSubjects(Subjects subjects) {
        this.subjects = subjects;
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
}
