package com.jumpplus.tracker.populator;

import com.jumpplus.tracker.connection.ConnectionManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class Populator {
    private static Connection conn = ConnectionManager.getConnection();

    public static void reset() {

        try (Statement statement = conn.createStatement();) {
            statement.execute("DROP SCHEMA IF EXISTS progresstracker;");
            statement.execute("CREATE SCHEMA progresstracker;");
            statement.execute("USE progresstracker;");
            
            statement.execute("CREATE TABLE users (" + "user_id INT auto_increment PRIMARY KEY,"
                    + "username varchar(255) unique," + "password varchar(255), " + "admin boolean);");
            
            statement.execute("CREATE TABLE artist (" + "artist_id INT NOT NULL AUTO_INCREMENT,"  + "artist_name VARCHAR(100), " 
            		+ "PRIMARY KEY (artist_id) " + "); ");
            
            statement.execute("CREATE TABLE genre (" + " genre_id INT NOT NULL AUTO_INCREMENT, " + "genre_name VARCHAR(100), " 
            		+ "PRIMARY KEY (genre_id) " + ");");
            
            
            
            statement.execute(
                    "CREATE TABLE albums (" + "album_id INT auto_increment PRIMARY KEY," + "artist_id INT, " + "genre_id INT, " 
                    		+ "album varchar(255)," + "release_year INT, " + "FOREIGN KEY (artist_id) REFERENCES artist(artist_id), "
                    		+ "FOREIGN KEY (genre_id) REFERENCES genre(genre_id)); ");
           
            // NOTE: May break code because of added song_Count variable.
            statement.execute("CREATE TABLE progress (" + "user_id INT NOT NULL," + "album_id INT NOT NULL,"
                    + "progress varchar(255)," + "song_count int," + "foreign key (user_id) references users(user_id),"
                    + "foreign key (album_id) references albums(album_id));");
            
            // NEW Tables
            statement.execute("CREATE TABLE songs (" + "song_id INT NOT NULL AUTo_INCREMENT," + "album_id INT," 
            		+ "song_title VARCHAR(100) NOT NULL," + "PRIMARY KEY (song_id)," + "FOREIGN KEY (album_id) REFERENCES albums(album_id));");
            
            
            
            // Albums has been updated to include the 0 in the release_year column. Can change later
            statement.execute("INSERT into users(username, password, admin) values ('sam2', md5('root'), false);");
            statement.execute("INSERT into users(username, password, admin) values ('charina1', md5('root'), false);");
            
            statement.execute("INSERT INTO artist VALUES (NULL, \"Default\");");
            statement.execute("INSERT INTO genre VALUES (NULL, \"Default\");");
            
            statement.execute("insert into albums values(NULL, 1, 1, \"Lauryn Hill, 'The Miseducation of Lauryn Hill'\", 0);");
            statement.execute("insert into albums values(NULL, 1, 1, \"Bob Dylan, 'Blood on the Tracks'\", 0);");
            statement.execute("insert into albums values(NULL, 1, 1, \"Prince and the Revolution, 'Purple Rain'\", 0);");
            statement.execute("insert into albums values(NULL, 1, 1, \"Fleetwood Mac, 'Rumours'\", 0);");
            statement.execute("insert into albums values(NULL, 1, 1, \"Nirvana, 'Nevermind'\", 0);");
            statement.execute("insert into albums values(NULL, 1, 1, \"The Beatles, 'Abbey Road'\", 0);");
            statement.execute("insert into albums values(NULL, 1, 1, \"Stevie Wonder, 'Songs in the Key of Life'\", 0);");
            statement.execute("insert into albums values(NULL, 1, 1, \"Joni Mitchell, 'Blue'\", 0);");
            statement.execute("insert into albums values(NULL, 1, 1, \"The Beach Boys, 'Pet Sounds'\", 0);");
            statement.execute("insert into albums values(NULL, 1, 1, \"Marvin Gaye, 'What's Going On'\", 0);");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

