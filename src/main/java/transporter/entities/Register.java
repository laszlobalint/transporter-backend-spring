package transporter.entities;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class Register {

    private String name;

    private String password;

    private String email;

    private String phoneNumber;

    public Register() {
    }

    public Register(@NotEmpty String name, @NotEmpty String password, @NotEmpty @Email String email, @NotEmpty String phoneNumber) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}