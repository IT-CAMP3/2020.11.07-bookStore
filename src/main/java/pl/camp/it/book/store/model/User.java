package pl.camp.it.book.store.model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "tuser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String login;
    private String pass;
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(int id, String name, String surname, String login, String pass, Role role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.pass = pass;
        this.role = role;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public enum Role {
        USER,
        ADMIN
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", pass='" + pass + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User) {
            User user = (User) obj;
            if(this.id == user.id
                    && this.login.equals(user.login)
                    && this.name.equals(user.name)
                    && this.pass.equals(user.pass)
                    && this.role == user.role
                    && this.surname.equals(user.surname)
            ) {
                return true;
            }
        }

        return false;
    }
}
