package np.com.krishna.nightbeforeexam.models;

import java.util.ArrayList;
import java.util.List;

public class Programs {
    private Long id;

    private String programName;

    private boolean active=true;

    private List<Subjects> subject=new ArrayList<>();

    private List<DiscussionForum> discussionForum = new ArrayList<>();

    private List<User> user=new ArrayList<>();

    public Programs(String programName, boolean active, List<Subjects> subject, List<DiscussionForum> discussionForum, List<User> user) {
        this.programName = programName;
        this.active = active;
        this.subject = subject;
        this.discussionForum = discussionForum;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Subjects> getSubject() {
        return subject;
    }

    public void setSubject(List<Subjects> subject) {
        this.subject = subject;
    }

    public List<DiscussionForum> getDiscussionForum() {
        return discussionForum;
    }

    public void setDiscussionForum(List<DiscussionForum> discussionForum) {
        this.discussionForum = discussionForum;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}
