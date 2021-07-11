package np.com.krishna.nightbeforeexam.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UserView {

    private Long id;
    private String fullname;
    private String username;
    private String email;
    private String password;
    private Long phone;
    private String institution;
    private String gender;
    private String address;
    private boolean active;
    private boolean isEnabled;
    private Programs courseDetail;
    private List<Posts> post=new ArrayList<>();
    private List<DiscussionForum>discussion=new ArrayList<>();
    private ProfilePicture profile;
    private String token;
    private String message;


    public UserView() {
    }

    public UserView(Long id, String fullname, String username, String email, String password, Long phone, String institution, String gender, String address, boolean active, boolean isEnabled, Programs courseDetail, List<Posts> post, List<DiscussionForum> discussion, ProfilePicture profile, String token, String message) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.institution = institution;
        this.gender = gender;
        this.address = address;
        this.active = active;
        this.isEnabled = isEnabled;
        this.courseDetail = courseDetail;
        this.post = post;
        this.discussion = discussion;
        this.profile = profile;
        this.token = token;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public Programs getCourseDetail() {
        return courseDetail;
    }

    public void setCourseDetail(Programs courseDetail) {
        this.courseDetail = courseDetail;
    }

    public List<Posts> getPost() {
        return post;
    }

    public void setPost(List<Posts> post) {
        this.post = post;
    }

    public List<DiscussionForum> getDiscussion() {
        return discussion;
    }

    public void setDiscussion(List<DiscussionForum> discussion) {
        this.discussion = discussion;
    }

    public ProfilePicture getProfile() {
        return profile;
    }

    public void setProfile(ProfilePicture profile) {
        this.profile = profile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
