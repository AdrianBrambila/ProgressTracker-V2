package com.jumpplus.tracker.dao;

import com.jumpplus.tracker.connection.ConnectionManager;
import com.jumpplus.tracker.model.Progress;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class ProgressDaoSQL implements ProgressDao {

    private Connection conn = ConnectionManager.getConnection();

    @Override
    public List<Progress> getAllUserTrackers(int u_id) {


        List<Progress> progList = new ArrayList<Progress>();

        try( Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM progress where user_id = " + u_id);){


            while(rs.next()) {
//                int uid = rs.getInt("user_id");
//                int aid = rs.getInt("album_id");
//                String prog = rs.getString("progress");
//                int songs = rs.getInt("song_count");
//
//
//                Progress nProg = new Progress(uid, aid, prog);
//                progList.add(nProg);
                int uid = rs.getInt("user_id");
                int aid = rs.getInt("album_id");
                String prog = rs.getString("progress");
                int songs = rs.getInt("song_count");
                int rating = rs.getInt("rating");


                Progress nProg = new Progress();
                nProg.setUser_id(uid);
                nProg.setAlbum_id(aid);
                nProg.setSong_count(songs);
                nProg.setProgress(prog);
                nProg.setRating(rating);

                progList.add(nProg);
            }

            return progList;

        } catch (SQLException e) {
            System.out.println("Could not retrieve list of trackers for user");
        }
        return null;
    }


    @Override
    public boolean updateProgress(Progress progress) {

        try ( PreparedStatement pstmt = conn.prepareStatement("update progress set user_id = ?, album_id = ?, progress = ? where album_id = ?")) {

            pstmt.setInt(1, progress.getUser_id());
            pstmt.setInt(2, progress.getAlbum_id());
            pstmt.setString(3, progress.getProgress());
            pstmt.setInt(4, progress.getAlbum_id());

            int i = pstmt.executeUpdate();

            if(i > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addProgress(Progress progress) {

        try( PreparedStatement pstmt = conn.prepareStatement("insert into progress(user_id, album_id, progress)values(?,?,?)")){

            pstmt.setInt(1, progress.getUser_id());
            pstmt.setInt(2, progress.getAlbum_id());
            pstmt.setString(3, progress.getProgress());

            int count = pstmt.executeUpdate();



            if(count > 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("add progress failed");
        }
        return false;
    }

    @Override
    public List<Progress> getAllProgress() {


        List<Progress> progList = new ArrayList<Progress>();

        try( Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM progress ");){


            while(rs.next()) {
                int uid = rs.getInt("user_id");
                int aid = rs.getInt("album_id");
                String prog = rs.getString("progress");
                int songs = rs.getInt("song_count");
                int rating = rs.getInt("rating");


                Progress nProg = new Progress();
                nProg.setUser_id(uid);
                nProg.setAlbum_id(aid);
                nProg.setSong_count(songs);
                nProg.setProgress(prog);
                nProg.setRating(rating);

                progList.add(nProg);

            }

            return progList;

        } catch (SQLException e) {
            System.out.println("Could not retrieve list of trackers for user");
        }
        return null;
    }

}
