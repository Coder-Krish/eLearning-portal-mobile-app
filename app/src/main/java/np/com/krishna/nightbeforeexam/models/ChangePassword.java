package np.com.krishna.nightbeforeexam.models;

public class ChangePassword {

    String username;
    String newPassword;
    String confirmPassword;

    public ChangePassword(String username, String newPassword, String confirmPassword) {
        this.username = username;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    public ChangePassword() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
