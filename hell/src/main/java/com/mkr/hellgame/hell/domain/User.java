package com.mkr.hellgame.hell.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    private String password;
    @OneToMany(mappedBy = "user")
    private Collection<InGameUser> inGameUsers = new ArrayList<>();

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

    public Collection<InGameUser> getInGameUsers() {
        return inGameUsers;
    }

    public void setInGameUsers(Collection<InGameUser> inGameUsers) {
        this.inGameUsers = inGameUsers;
    }
}
