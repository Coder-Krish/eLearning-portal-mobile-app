package np.com.krishna.nightbeforeexam.models;

public class RepliesOnDiscussion {

    private Long id;
    private String reply;
    private DiscussionForum discussion;

    private User user;

    private String repliedAt;

    private boolean active;

    public RepliesOnDiscussion(String reply, DiscussionForum discussion, User user, String repliedAt, boolean active) {
        this.reply = reply;
        this.discussion = discussion;
        this.user = user;
        this.repliedAt = repliedAt;
        this.active = active;
    }

    public RepliesOnDiscussion() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public DiscussionForum getDiscussion() {
        return discussion;
    }

    public void setDiscussion(DiscussionForum discussion) {
        this.discussion = discussion;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRepliedAt() {
        return repliedAt;
    }

    public void setRepliedAt(String repliedAt) {
        this.repliedAt = repliedAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
