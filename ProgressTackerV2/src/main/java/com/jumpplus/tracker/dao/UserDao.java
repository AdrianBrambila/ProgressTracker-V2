package com.jumpplus.tracker.dao;

import com.jumpplus.tracker.model.User;

public interface UserDao {

    public User loginUser(User user);

    public User getUsername(String username);

    public User getUserId(int u_id);

    boolean createUser(String username, String password);
}
