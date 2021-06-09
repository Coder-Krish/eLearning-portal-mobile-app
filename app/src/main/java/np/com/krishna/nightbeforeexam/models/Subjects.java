package np.com.krishna.nightbeforeexam.models;

import java.util.ArrayList;
import java.util.List;

public class Subjects {
    private Long id;

    private String subjectsName;

    private String semester;

    private boolean active=true;

    private Programs program;

    private List<Notes> note=new ArrayList<>();

    private List<DiscussionForum> discussion=new ArrayList<>();

    public Subjects(String subjectsName, String semester, boolean active, Programs program, List<Notes> note, List<DiscussionForum> discussion) {
        this.subjectsName = subjectsName;
        this.semester = semester;
        this.active = active;
        this.program = program;
        this.note = note;
        this.discussion = discussion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectsName() {
        return subjectsName;
    }

    public void setSubjectsName(String subjectsName) {
        this.subjectsName = subjectsName;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Programs getProgram() {
        return program;
    }

    public void setProgram(Programs program) {
        this.program = program;
    }

    public List<Notes> getNote() {
        return note;
    }

    public void setNote(List<Notes> note) {
        this.note = note;
    }

    public List<DiscussionForum> getDiscussion() {
        return discussion;
    }

    public void setDiscussion(List<DiscussionForum> discussion) {
        this.discussion = discussion;
    }
}
