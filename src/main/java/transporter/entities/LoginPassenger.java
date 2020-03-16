package transporter.entities;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class LoginPassenger {

    private String plainPassword;

    private String email;

    public LoginPassenger() {
    }

    public LoginPassenger(@NotEmpty @Email String plainPassword, @NotEmpty String email) {
        this.plainPassword = plainPassword;
        this.email = email;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}