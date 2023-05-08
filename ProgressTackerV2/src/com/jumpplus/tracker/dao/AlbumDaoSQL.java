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
import com.jumpplus.tracker.model.User;

public class AlbumDaoSQL implements AlbumDao{
    private Connection conn = ConnectionManager.getConnection();

    @Override
    public Album getAlbumId(int a_id) {

        try( PreparedStatement pstmt = conn.prepareStatement("select al.album_id, al.album, ar.artist_name, g.genre_name, al.song_number, al.release_year from artist ar join albums al on al.artist_id = ar.artist_id join genre g on al.genre_id = g.genre_id where album_id = ?")){

            pstmt.setInt(1, a_id);

            ResultSet rs = pstmt.executeQuery();
            Album album = new Album();

            if(rs.next()) {
                int album_id = rs.getInt("album_id");
                String album_name = rs.getString("album");
                String artist = rs.getString("artist_name");
                String genre = rs.getString("genre_name");
                int release = rs.getInt("release_year");
                int songCount = rs.getInt("song_number");



                album.setAlbum_id(album_id);
                album.setAlbumName(album_name);
                album.setArtist(artist);
                album.setGenre(genre);
                album.setReleaseYear(release);
                album.setSongCount(songCount);

            }

            rs.close();

            return album;

        } catch (SQLException e) {
            System.out.println("Could not find album id" + a_id);
        }

        return null;
    }

    @Override
    public String getAlbumNameById(int a_id) {
        try( PreparedStatement pstmt = conn.prepareStatement("select album from  albums  where album_id = ?")){

            pstmt.setInt(1, a_id);

            ResultSet rs = pstmt.executeQuery();
            String album_name = "";
            if(rs.next()) {
                album_name = rs.getString("album");


            }

            rs.close();

            return album_name;

        } catch (SQLException e) {
            System.out.println("Could not find album");
        }
        return null;
    }

    @Override
    public List<Album> getAllAlbums() {

        List<Album> albList = new ArrayList<Album>();

        try( Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("select al.album_id, al.album, ar.artist_name, g.genre_name, al.release_year from artist ar join albums al on al.artist_id = ar.artist_id join genre g on al.genre_id = g.genre_id order by al.album_id");){

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
    public List<String> getAllArtists() {
        List<String> artList = new ArrayList<String>();

        try( Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("select * from artist");){

            while(rs.next()) {
                String artist = rs.getString("artist_name");


                artList.add(artist);

            }

        } catch (SQLException e) {
            System.out.println("Could not retrieve list of artists");
        }
        return artList;
    }

    @Override
    public List<String> getAllGenres() {
        List<String> genList = new ArrayList<String>();

        try( Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("select * from genre");){

            while(rs.next()) {
                String genre = rs.getString("genre_name");


                genList.add(genre);

            }

        } catch (SQLException e) {
            System.out.println("Could not retrieve list of genres");
        }
        return genList;
    }

    @Override
    public boolean addArtist(String artist) {
       // try( PreparedStatement pstmt = conn.prepareStatement("INSERT into artist(?, ?)values(?, ?)")){
        try( PreparedStatement pstmt = conn.prepareStatement("INSERT into artist(artist_name)values(?)")){

            //pstmt.setInt(1, 1);
            pstmt.setString(1, artist);


            int count = pstmt.executeUpdate();

            if(count > 0) {
                return true;
            }


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("genre add failed");
        }
        return false;
    }

    @Override
    public boolean addGenre(String genre) {
        //try( PreparedStatement pstmt = conn.prepareStatement("INSERT into genre(?, ?)values(?, ?)")){
        try( PreparedStatement pstmt = conn.prepareStatement("INSERT into genre(genre_name)values(?)")){

           // pstmt.setInt(1, 1);
            pstmt.setString(1, genre);


            int count = pstmt.executeUpdate();

            if(count > 0) {
                return true;
            }


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("genre add failed");
        }
        return false;
    }

    @Override
    public boolean addAlbum(Album album, int artist_id, int genre_id) {



        try( PreparedStatement pstmt = conn.prepareStatement("INSERT into albums(artist_id, genre_id, album, song_number, release_year)values(?, ?, ?,?,?)")){
        //try( PreparedStatement pstmt = conn.prepareStatement("INSERT into albums(?, ?, ?,?,?,?)values(?, ?, ?,?,?,?)")){

            //pstmt.setInt(1, 1);
            pstmt.setInt(1, artist_id);  //artistid
            pstmt.setInt(2, genre_id);   //genreid
            pstmt.setString(3, album.getAlbumName());  //albumname
            pstmt.setInt(4, album.getSongCount());
            pstmt.setInt(5, album.getReleaseYear());

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

    @Override
    public int getArtistId(String artist) {
        try( PreparedStatement pstmt = conn.prepareStatement("select artist_id from artist  where artist_name = ?")){

            pstmt.setString(1, artist);

            ResultSet rs = pstmt.executeQuery();
            int artist_id = 0;
            if(rs.next()) {
                artist_id = rs.getInt("artist_id");

            }

            rs.close();

            return artist_id;

        } catch (SQLException e) {
            System.out.println("Could not find artist id" );
        }


        return 0;
    }

    @Override
    public int getGenreId(String genre) {
        try( PreparedStatement pstmt = conn.prepareStatement("select genre_id from genre  where genre_name = ?")){

            pstmt.setString(1, genre);

            ResultSet rs = pstmt.executeQuery();
            int genre_id = 0;
            if(rs.next()) {
                genre_id = rs.getInt("genre_id");

            }

            rs.close();

            return genre_id;

        } catch (SQLException e) {
            System.out.println("Could not find genre id" );
        }
        return 0;
    }

}

//    String artistName = album.getArtist();
//    int artist_id = 0;
//    int genre_id = 0;
//        try( PreparedStatement pstmt = conn.prepareStatement("INSERT into artist(?, ?)values(?, ?)")){
//
//
//                pstmt.setInt(1, 1);
//                pstmt.setString(2, album.getArtist());  //need to find a way to get album id from album name
//
//
//                int count = pstmt.executeUpdate();
//
//                if(count > 0) {
//                return true;
//                }
//
//
//                } catch (SQLException e) {
//                e.printStackTrace();
//                System.out.println("artist add failed");
//                }
//                try( PreparedStatement pstmt = conn.prepareStatement("INSERT into genre(?, ?)values(?, ?)")){
//
//
//                pstmt.setInt(1, 1);
//                pstmt.setString(2, album.getGenre());  //need to find a way to get album id from album name
//
//
//                int count = pstmt.executeUpdate();
//
//                if(count > 0) {
//                return true;
//                }
//
//
//                } catch (SQLException e) {
//                e.printStackTrace();
//                System.out.println("artist add failed");
//                }
//                try( PreparedStatement pstmt = conn.prepareStatement("select aritst_id from artist where artist_name = ?")){
//
//                pstmt.setString(1, album.getArtist());
//
//                ResultSet rs = pstmt.executeQuery();
//                User user = null;
//
//                if(rs.next()) {
//                artist_id = rs.getInt("artist_id");
//
//
//                }
//
//                rs.close();
//                // use getter for the username
//                //return user;
//
//                } catch (SQLException e) {
//                System.out.println("Could not find artist id" );
//                }
//                try( PreparedStatement pstmt = conn.prepareStatement("select genre_id from genre where genre_name = ?")){
//
//                pstmt.setString(1, album.getGenre());
//
//                ResultSet rs = pstmt.executeQuery();
//                User user = null;
//
//                if(rs.next()) {
//                genre_id = rs.getInt("genre_id");
//
//
//                }
//
//                rs.close();
//                // use getter for the username
//                //return user;
//
//                } catch (SQLException e) {
//                System.out.println("Could not find genre id" );
//                }