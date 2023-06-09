package com.jumpplus.tracker.dao;

import com.jumpplus.tracker.connection.ConnectionManager;
import com.jumpplus.tracker.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class UserDaoSQL implements UserDao{

    private Connection conn = ConnectionManager.getConnection();

    @Override
    public User getUsername(String username) {

        try( PreparedStatement pstmt = conn.prepareStatement("select * from users where username = ?")){

            pstmt.setString(2, username);

            ResultSet rs = pstmt.executeQuery();
            User user = null;

            if(rs.next()) {
                int user_id = rs.getInt("user_id");
                String usname = rs.getString("username");
                String password = rs.getString("password");
                boolean admin = rs.getBoolean("admin");

                user = new User(user_id, usname, password, admin);

            }

            rs.close();
            // use getter for the username
            return user;

        } catch (SQLException e) {
            System.out.println("Could not find username");
        }

        return null;
    }

    @Override
    public User getUserId(int u_id) {

        try( PreparedStatement pstmt = conn.prepareStatement("select * from users where user_id = ?")){

            pstmt.setInt(1, u_id);

            ResultSet rs = pstmt.executeQuery();
            User user = null;

            if(rs.next()) {
                int user_id = rs.getInt("user_id");
                String usname = rs.getString("username");
                String password = rs.getString("password");
                boolean admin = rs.getBoolean("admin");

                user = new User(user_id, usname, password, admin);

            }

            rs.close();
            // use getter for the username
            return user;

        } catch (SQLException e) {
            System.out.println("Could not find user id" + u_id);
        }

        return null;
    }

    @Override
    public boolean createUser(String username, String password, boolean admin) {

        try {

            conn.createStatement().execute("INSERT into users(username, password, admin) values ('" + username + "', md5('" + password +  "'), " + admin + ")");
        } catch (SQLException e) {
            return false;
        }
        return true;
    }



    @Override
    public User loginUser(User user) {

        try( PreparedStatement pstmt = conn.prepareStatement("select * from users where username = ? and password = md5(?) ")){


            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());

            ResultSet rs = pstmt.executeQuery();
            User user1 = null;

            if(rs.next()) {
                int usId = rs.getInt("user_id");
                String usname = rs.getString("username");
                String password = rs.getString("password");
                boolean admin = rs.getBoolean("admin");

                user1 = new User(usId, usname, password, admin);

            }

            rs.close();
            if(user1 != null) {
                System.out.println("Account found" + "\n" + "Welcome " + user.getUsername());
            }
            return user1;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not find user's account, please register");
        }

        return null;
    }


    @Override
    public boolean deleteUser(int u_id) {
        try{
            PreparedStatement pstmt = conn
                    .prepareStatement("delete from student_classes where sid = ?");
            pstmt.setInt(1, u_id);


            int i = pstmt.executeUpdate();

            if (i > 0) {
                return true;
            }

        }
        catch (SQLException e){
            System.out.println("Failed to delete student");
        }
        return false;
    }


}
