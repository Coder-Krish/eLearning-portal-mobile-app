package np.com.krishna.nightbeforeexam.models;

public class SignupRequest {

    private String fullname;
    private String username;
    private String email;
    private String password;
    private Long phone;
    private String institution;
    private String course;
    private Long programId;
    private String programName;
    private String gender;
    private String address;

    public String getFullname() {
        return fullname;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Long getPhone() {
        return phone;
    }

    public String getInstitution() {
        return institution;
    }

    public String getCourse() {
        return course;
    }

    public Long getProgramId() {
        return programId;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
