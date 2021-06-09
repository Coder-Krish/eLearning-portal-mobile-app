package np.com.krishna.nightbeforeexam.models;

public class Comments {

    private Long id;
    private String comment;
    private Posts post;
    private User user;

    private String CommentedAt;

    private boolean active=true;

    public Comments(String comment, Posts post, User user, String commentedAt, boolean active) {
        this.comment = comment;
        this.post = post;
        this.user = user;
        CommentedAt = commentedAt;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Posts getPost() {
        return post;
    }

    public void setPost(Posts post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCommentedAt() {
        return CommentedAt;
    }

    public void setCommentedAt(String commentedAt) {
        CommentedAt = commentedAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
