package com.mkr.hellgame.hell.entities;

import javax.persistence.*;

@Entity
public class InGameUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "systemUserId")
    private SystemUser systemUser;

    private String login;

    @ManyToOne
    @JoinColumn(name = "gameVariantId")
    private GameVariant gameVariant;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SystemUser getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
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
