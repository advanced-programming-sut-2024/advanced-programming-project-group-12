package com.mygdx.game.controller.remote;

import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.mygdx.game.model.user.User;

import java.util.TreeSet;

public class TornomentHandler {
    TreeSet<User> users;

    public TornomentHandler(User user) {
        users = new TreeSet<>();
        this.users.add(user);
    }
}
