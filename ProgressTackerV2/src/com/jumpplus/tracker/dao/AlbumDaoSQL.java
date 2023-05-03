package com.jumpplus.tracker.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jumpplus.tracker.connection.ConnectionManager;
import com.jumpplus.tracker.model.Album;

public class AlbumDaoSQL implements AlbumDao{
    private Connection conn = ConnectionManager.getConnection();

    @Override
    public Album getAlbumId(int a_id) {

        try( PreparedStatement pstmt = conn.prepareStatement("select al.album_id, al.album, ar.artist_name, g.genre_name, al.release_year from artist ar join albums al on al.artist_id = ar.artist_id join genre g on al.genre_id = g.genre_id where album_id = ?")){

            pstmt.setInt(1, a_id);

            ResultSet rs = pstmt.executeQuery();
            Album album = new Album();

            if(rs.next()) {
                int album_id = rs.getInt("album_id");
                String album_name = rs.getString("album");
                String artist = rs.getString("artist_name");
                String genre = rs.getString("genre_name");
                int release = rs.getInt("release_year");



                album.setAlbum_id(album_id);
                album.setAlbumName(album_name);
                album.setArtist(artist);
                album.setGenre(genre);
                album.setReleaseYear(release);

            }

            rs.close();

            return album;

        } catch (SQLException e) {
            System.out.println("Could not find album id" + a_id);
        }

        return null;
    }

    @Override
    public List<Album> getAllAlbums() {

        List<Album> albList = new ArrayList<Album>();

        try( Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("select al.album_id, al.album, ar.artist_name, g.genre_name, al.release_year from artist ar join albums al on al.artist_id = ar.artist_id join genre g on al.genre_id = g.genre_id");){

            while(rs.next()) {
                int album_id = rs.getInt("album_id");
                String album_name = rs.getString("album");
                String artist = rs.getString("artist_name");
                String genre = rs.getString("genre_name");
                int release = rs.getInt("release_year");


                Album album = new Album();
                album.setAlbum_id(album_id);
                album.setAlbumName(album_name);
                album.setArtist(artist);
                album.setGenre(genre);
                album.setReleaseYear(release);

                albList.add(album);

            }

        } catch (SQLException e) {
            System.out.println("Could not retrieve list of albums");
        }
        return albList;
    }

    @Override
    public boolean addAlbum(Album album) {

        try( PreparedStatement pstmt = conn.prepareStatement("INSERT into albums(?, ?, ?, ?, ?)values(?, ?, ?,?,?)")){

            pstmt.setInt(1, album.getAlbum_id());
            pstmt.setInt(2, 1);  //need to find a way to get album id from album name
            pstmt.setInt(3, 1);
            pstmt.setString(4, album.getAlbumName());
            pstmt.setInt(4, album.getReleaseYear());

            int count = pstmt.executeUpdate();

            if(count > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("album add failed");
        }
        return false;
    }

}

