package com.jumpplus.tracker.controller;

import java.util.List;
import java.util.Scanner;

import com.jumpplus.tracker.dao.AlbumDaoSQL;
import com.jumpplus.tracker.model.Album;
import com.jumpplus.tracker.model.Progress;
import com.jumpplus.tracker.model.User;

public class RunnerController {

    public static void menuDisplay() {
        System.out.println("+====================================+");
        System.out.println("              TRACKING APP");
        System.out.println("+====================================+");
        System.out.println("\n1. Login\n2. Register\n0. Quit\n");
    }
    
    
    public static void menu(User user) {

        System.out.println("\n+============================================+");
        System.out.println("  Hello, " + user.getUsername());
        System.out.println("| Welcome to the Album Progress Tracker!     |");
        System.out.println("|                                            |");
        System.out.println("| Please choose from the following options:  |");
        System.out.println("|                                            |");
        System.out.println("| 1: Add Album                               |");
        System.out.println("| 2: Add Progress                            |");
        System.out.println("| 3: Update Progress                         |");
        System.out.println("| 4: List Albums                             |");
        System.out.println("| 5: LOGOUT                                  |");
        System.out.println("|                                            |");
        System.out.println("+============================================+");
    }
    public static void adminMenu(User user) {

        System.out.println("\n+============================================+");
        System.out.println("  Hello, " + user.getUsername());
        System.out.println("| Welcome to the Album Progress Tracker!     |");
        System.out.println("|                                            |");
        System.out.println("| Please choose from the following options:  |");
        System.out.println("|                                            |");
        System.out.println("| 1: Add Album                               |");
        System.out.println("| 2: Add Progress                            |");
        System.out.println("| 3: Update Progress                         |");
        System.out.println("| 4: List Albums                             |");
        System.out.println("| 5: EDIT ALBUMS                             |");
        System.out.println("| 6: LOGOUT                                  |");
        System.out.println("|                                            |");
        System.out.println("+============================================+");
    }
    public static void progressMenu() {

        System.out.println("Please choose your progress:			  ");
        System.out.println("                                          ");
        System.out.println("6 - Not Started                           ");
        System.out.println("7 - In Progress                           ");
        System.out.println("8 - Completed                            \n");
    }
    public static void progressUpdateMenu() {

        System.out.println("Please choose your updated progress:      ");
        System.out.println("                                          ");
        System.out.println("6 - Not Started                           ");
        System.out.println("7 - In Progress                           ");
        System.out.println("8 - Completed                            \n");
    }

    // public Album(int album_id, String albumName, String artist, String genre, int releaseYear)
    public static void addAlbum(Scanner scan, AlbumDaoSQL albumCaller) {
        System.out.println("What's the name of the new album?");
        String albumName = scan.nextLine();
        //String albumName = testVar + scan.nextLine();
        System.out.println("Who is the Artist? ");
        String artist = scan.nextLine();

        System.out.println("What is the Genre? ");
        String genre = scan.nextLine();

        System.out.println("When was the album released? ");
        int releseYear = scan.nextInt();
        scan.nextLine();


        Album albumAdded = new Album(1, albumName, artist, genre, releseYear);
        boolean addResult = albumCaller.addAlbum(albumAdded);
        if (addResult) {
            System.out.println(albumAdded);
            System.out.println("Album successfully added");
        } else {
            System.out.println("Could not add album");
        }
    }



    public static void viewAlbums(List<Progress> progList) {

        AlbumDaoSQL albumCaller = new AlbumDaoSQL();
        if (progList.isEmpty()) {
            System.out.println("\nYou aren't tracking any albums.\n");
        }
        List<Album> albums = albumCaller.getAllAlbums();
        progList.forEach(progress -> {



            Album progressAlbum =
                    albums.stream().filter(album -> album.getAlbum_id() == progress.getAlbum_id())
                            .findFirst().get();
            if (progressAlbum.getAlbum_id() < 10) System.out.printf("\n %s - %s -> %s", progressAlbum.getAlbum_id(),
                    progressAlbum.getAlbumName(), progress.getProgress());
            else System.out.printf("\n%s - %s -> %s", progressAlbum.getAlbum_id(),
                    progressAlbum.getAlbumName(), progress.getProgress());
        });
    }


}
