package com.jumpplus.tracker.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.jumpplus.tracker.dao.AlbumDaoSQL;
import com.jumpplus.tracker.model.Album;
import com.jumpplus.tracker.model.Progress;
import com.jumpplus.tracker.model.User;

public class RunnerController {

    static AlbumDaoSQL adao = new AlbumDaoSQL();

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
        System.out.println("| 1: Add Progress                            |");
        System.out.println("| 2: Update Progress                         |");
        System.out.println("| 3: List Albums                             |");
        System.out.println("| 4: List Averages                           |");
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
        System.out.println("| 1: Add Progress                            |");
        System.out.println("| 2: Update Progress                         |");
        System.out.println("| 3: List Albums                             |");
        System.out.println("| 4: List Averages                           |");
        System.out.println("| 5: LOGOUT                                  |");
        System.out.println("| 6: Add Album                               |");
        System.out.println("| 7: EDIT ALBUMS                             |");
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

    public static void updateAlbumMenu(){
        System.out.println("What would you like to update?            ");
        System.out.println("                                          ");
        System.out.println("1 - Artist                                ");
        System.out.println("2 - Genre                                 ");
        System.out.println("3 - Album Name                            ");
        System.out.println("4 - Number of Songs                       ");
        System.out.println("5 - Release Year \n                       ");
    }

    // public Album(int album_id, String albumName, String artist, String genre, int releaseYear)
    public static void addAlbum(Scanner scan, AlbumDaoSQL albumCaller) {

        List<String> artList = adao.getAllArtists();
        List<String> genList = adao.getAllGenres();
        int artist_id = 0;
        int genre_id = 0;
        System.out.println("What's the name of the new album?");
        String albumName = scan.nextLine();

        System.out.println("Who is the Artist? ");
        String artist = scan.nextLine();

        if(artList.contains(artist)){
            artist_id = adao.getArtistId(artist);
        }
        else{
            adao.addArtist(artist);
            artist_id = adao.getArtistId(artist);
        }



        System.out.println("What is the Genre? ");
        String genre = scan.nextLine();
        if(genList.contains(genre)){
            genre_id = adao.getGenreId(genre);
        }
        else{
            adao.addGenre(genre);
            genre_id = adao.getGenreId(genre);

        }

        System.out.println("When was the album released? ");
        int releseYear = scan.nextInt();
        scan.nextLine();

        System.out.println("How many songs are in the Album?");
        int songCount = scan.nextInt();
        scan.nextLine();


        Album albumAdded = new Album(1, albumName, artist, genre, songCount, releseYear);
        boolean addResult = albumCaller.addAlbum(albumAdded, artist_id, genre_id);
        if (addResult) {
            System.out.println(albumAdded);
            System.out.println("Album successfully added");
        } else {
            System.out.println("Could not add album");
        }
    }
    public static void updateAlbum(Scanner scan){
        System.out.println("What album would you like to update?");

        List<Album> albList = adao.getAllAlbums();

        System.out.println("\n" + String.format("%-2s - %-38s   %-25s   %-15s Songs", "ID", "Album", "Artist", "Genre"));


        for (Album a : albList){
            System.out.println(String.format("%-2s", a.getAlbum_id())
                    + " | " + String.format("%-38s", a.getAlbumName())
                    + " | " + String.format("%-25s", a.getArtist())
                    + " | " + String.format("%-15s", a.getGenre())
                    + "|" + " Songs: " + a.getSongCount());

        }
        int choice = scan.nextInt();
        scan.nextLine();
        Album a = adao.getAlbumId(choice);
        List<String> artList = adao.getAllArtists();
        List<String> genList = adao.getAllGenres();
        int artist_id = adao.getArtistId(a.getArtist());
        int genre_id = adao.getGenreId(a.getGenre());

        updateAlbumMenu();
        choice = scan.nextInt();
        scan.nextLine();
        boolean b = true;
        while(b) {
            switch (choice) {
                case 1: //artist
                    System.out.println("What is the name of the new artist?");

                    String artist = scan.nextLine();

                    if (artList.contains(artist)) {
                        artist_id = adao.getArtistId(artist);
                    } else {
                        adao.addArtist(artist);
                        artist_id = adao.getArtistId(artist);
                    }
                    b=false;

                    break;
                case 2: //genre
                    System.out.println("What is the name of the new genre?");
                    String genre = scan.nextLine();
                    if (genList.contains(genre)) {
                        genre_id = adao.getGenreId(genre);
                    } else {
                        adao.addGenre(genre);
                        genre_id = adao.getGenreId(genre);

                    }
                    b=false;

                    break;
                case 3: //name
                    System.out.println("What is the new album name?");
                    String album = scan.nextLine();
                    a.setAlbumName(album);
                    b=false;

                    break;
                case 4: //song count
                    System.out.println("What is the new song count?");
                    int songs = scan.nextInt();
                    scan.nextLine();
                    a.setSongCount(songs);
                    b=false;

                    break;
                case 5: //release year
                    System.out.println("What is the new release year?");
                    int year = scan.nextInt();
                    scan.nextLine();
                    a.setReleaseYear(year);
                    b=false;
                    break;


            }
        }
        if(adao.updateAlbum(a, artist_id, genre_id)){
            System.out.println("Album updated!");

        }
        else{
            System.out.println("Album not updated successfully");
        }




    }


    public static void viewAlbums(List<Progress> progList) {

        AlbumDaoSQL albumCaller = new AlbumDaoSQL();
        if (progList.isEmpty()) {
            System.out.println("\nYou aren't tracking any albums.\n");
        }
        List<Album> albums = albumCaller.getAllAlbums();

    }


    public static String displayProgressBar(int progress, int total) {
        double percent = (double) progress / (double) total;
        double outOfTwenty = percent * 20;
        int progressToPrint = (int) outOfTwenty;

        String bar = "|";
        for (int i = 1; i <= 20; i++) {
            if (i <= progressToPrint) {
                bar = bar + "*";
            } else {
                bar = bar + "-";
            }
        }
        bar = bar + "|";

        return bar;
    }
}
