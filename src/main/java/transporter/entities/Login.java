package transporter.entities;

        import javax.validation.constraints.Email;
        import javax.validation.constraints.NotEmpty;

public class Login {

    private String plainPassword;

    private String email;

    public Login() {
    }

    public Login(@NotEmpty String plainPassword, @NotEmpty @Email String email) {
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