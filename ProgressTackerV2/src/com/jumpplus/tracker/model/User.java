package com.jumpplus.tracker.model;

public class User {

    private int user_id;
    private String username;
    private String password;
    private boolean admin;

    public User(int user_id, String username, String password, boolean admin) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public User(String username, String password, boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public User() {

    }

    /**
     * @return the user_id
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    

    /**
	 * @return the admin
	 */
	public boolean isAdmin() {
		return admin;
	}

	/**
	 * @param admin the admin to set
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
    public String toString() {
        return "User [user_id=" + user_id + ", username=" + username + ", password=" + password + "]";
    }

}
