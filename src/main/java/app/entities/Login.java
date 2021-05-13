package app.entities;

import java.io.Serializable;
import java.util.Objects;

public class Login implements Serializable {

    private int id;
    private String login;
    private String password;
    private String email;
    private String fullName;
    private int roleId;
    private int phone;

    public Login(int id, String login, String password, String fullName, String email, int phone, int roleId) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.roleId = roleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Login login1 = (Login) o;
        return id == login1.id &&
                roleId == login1.roleId &&
                Objects.equals(login, login1.login) &&
                Objects.equals(password, login1.password) &&
                Objects.equals(email, login1.email) &&
                Objects.equals(fullName, login1.fullName) &&
                Objects.equals(phone, login1.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, email, fullName, roleId, phone);
    }

    @Override
    public String toString() {
        return "Login{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", roleId=" + roleId +
                ", phone='" + phone + '\'' +
                '}';
    }
}
