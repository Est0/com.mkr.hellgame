package com.mkr.hellgame.hell.domain;

import javax.persistence.*;

@Entity
public class InGameUser {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    private String login;
    @ManyToOne
    private GameVariant gameVariant;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public GameVariant getGameVariant() {
        return gameVariant;
    }

    public void setGameVariant(GameVariant gameVariant) {
        this.gameVariant = gameVariant;
    }
}
