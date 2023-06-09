package com.jumpplus.tracker.runner;

import com.jumpplus.tracker.controller.RunnerController;
import com.jumpplus.tracker.dao.AlbumDaoSQL;
import com.jumpplus.tracker.dao.ProgressDaoSQL;
import com.jumpplus.tracker.dao.UserDaoSQL;
import com.jumpplus.tracker.exceptions.LoginException;
import com.jumpplus.tracker.exceptions.TrackingException;
import com.jumpplus.tracker.model.Album;
import com.jumpplus.tracker.model.Progress;
import com.jumpplus.tracker.model.User;
//import com.jumpplus.tracker.populator.Populator;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;




public class Runner {


    public static void main(String[] args) {

        //Populator.reset();

        Scanner scan = new Scanner(System.in);


        UserDaoSQL userCaller = new UserDaoSQL();
        String welcome = "\nWelcome to our tracking app.\n";

        // String loginMenu = "1 or l for (L)ogin\n2 or r for (R)egister\n0 or q for (Q)uit\ndont be a quitter";

        boolean isLogging = true;

        System.out.println(welcome);
        //System.out.println(loginMenu);
        RunnerController.menuDisplay();

        do {


            String ans = scan.nextLine().toUpperCase();
            if (ans.isEmpty()) {
                System.out.println(welcome);
                // System.out.println(loginMenu);
                RunnerController.menuDisplay();
                continue;
            }

            switch (ans.charAt(0)) {
              //  case 'L':
                case '1':

                    System.out.println("What's your username?");
                    try {
                        String username = scan.nextLine();
                        System.out.println("\nWhat's your password?");
                        String password = scan.nextLine();
                        User loggedUser = new User(username, password);
                        User verifiedUser = userCaller.loginUser(loggedUser);
                        if (verifiedUser != null) {
                            // call menu function
                            System.out.println("You are logged in");
                            loggedMenu(verifiedUser, scan);


                        } else {
                            // throw LoginException and catch it
                            throw new LoginException();

                        }
                    } catch (InputMismatchException e) {
                        e.printStackTrace();
                        System.out.println("Must enter a letter");
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        System.out.println("Account not found, please try again");
                    } catch (LoginException e) {
                        System.out.println("login failed");
                    }
                    break;
               // case 'R':
                case '2':

                    System.out.println("Enter (1) if you would like to create a User account or (2) for an Admin account");

                    //String admin = scan.nextLine();
                    //String user = scan.nextLine();
                    int choice = scan.nextInt();
                    scan.nextLine();
                    boolean admin = false;
                    if(choice == 1 ) {
                        admin = false;
                    }
                    else if (choice == 2){
                        admin= true;
                    }else {
                        System.out.println("Invalid choice!");
                    }

                    System.out.println(
                            "\nPlease try to use a unique username and a difficult password.\nWe store your password "
                                    + "with MD5 message-digest algorithm, 128bit hash value.");
                    System.out.println("\nusername:");
                    String newUsername = scan.nextLine();
                    System.out.println("\npassword:");
                    String password = scan.nextLine();
                    boolean result = userCaller.createUser(newUsername, password, admin);
                    if (result) {
                        System.out.println("\nUser " + newUsername + " created successfully.");
                    } else {
                        System.out.println("\nError, try again with other username.");
                    }
                    System.out.println(welcome);
                    // System.out.println(loginMenu);
                    RunnerController.menuDisplay();
                    break;

              //  case 'Q':
                case '0':
                    isLogging = false;
                    System.out.println("Thanks for using our progress tracking app. Have a great day!");

                    break;

                default:
                    System.out.println("Input must be L for login or Q for quit");
                    break;
            }

        } while (isLogging);

        scan.close();
    }

    public static void loggedMenu(User user, Scanner scan) {

        AlbumDaoSQL adao = new AlbumDaoSQL();
        ProgressDaoSQL pdao = new ProgressDaoSQL();


        int ans;
        try {
            do {
            	if (user.isAdmin()) {
            		RunnerController.adminMenu(user);
            	}
            	else {
            		RunnerController.menu(user);
            	}
               

                ans = scan.nextInt();
                scan.nextLine();

                List<Progress> progList = pdao.getAllUserTrackers(user.getUser_id());

                switch (ans) {


                    case 1:
                        // Add Progress

                        int userId = user.getUser_id();
                        System.out.println("What's the album id?");
                        System.out.println("---------------------------------------------------------------------------------");

                        List<Album> albList = adao.getAllAlbums();

                        System.out.println("\n" + String.format("%-2s - %-38s   %-25s   %-15s Songs", "ID", "Album", "Artist", "Genre"));


                        for (Album a : albList){
                            System.out.println(String.format("%-2s", a.getAlbum_id())
                            		+ " | " + String.format("%-38s", a.getAlbumName())
                            		+ " | " + String.format("%-25s", a.getArtist())
                                    + " | " + String.format("%-15s", a.getGenre())
                                    + "|" + " Songs: " + a.getSongCount());

                        }

                        int songCount = 0;
                        int rating = 0;

                        int albumId = scan.nextInt();

                        int choice;
                        String progressChoice;
                        String[] progressStatus = { "Not started", "In-progress", "Completed", "" };

                        RunnerController.progressMenu();

                        choice = scan.nextInt();
                        scan.nextLine();



                        if(choice == 7){

                            System.out.println("How many songs have you listened to?");
                            songCount = scan.nextInt();
                            scan.nextLine();

                        }
                        if(choice == 8){
                            System.out.println("What would you rate the album out of 5?");
                            rating = scan.nextInt();
                            scan.nextLine();

                            Album a = adao.getAlbumId(albumId);
                            songCount = a.getSongCount();


                        }



                        String message = "Invalid progress entered";
                        boolean stillChoosing = true;
                        while (stillChoosing) {
                            switch (choice) {
                                case 6:
                                    progressChoice = progressStatus[0];
                                    Progress progressAdded = new Progress(userId, albumId, progressChoice, songCount, rating);
                                    boolean progressAddResult = pdao.addProgress(progressAdded);
                                    if (progressAddResult) {
                                        System.out.println(progressAdded);
                                        System.out.println("Progress tracker successfully added");
                                    } else {
                                        System.out.println(message);
                                        throw new TrackingException(message);
                                    }
                                    stillChoosing = false;
                                    break;
                                case 7:
                                    progressChoice = progressStatus[1];
                                    Progress progressAdded2 = new Progress(userId, albumId, progressChoice, songCount, rating);;
                                    boolean progressAddResult2 = pdao.addProgress(progressAdded2);
                                    if (progressAddResult2) {
                                        System.out.println(progressAdded2);
                                        System.out.println("Progress tracker successfully added");
                                    } else {
                                        System.out.println(message);
                                        throw new TrackingException(message);
                                    }
                                    stillChoosing = false;
                                    break;
                                case 8:
                                    progressChoice = progressStatus[2];
                                    Progress progressAdded3 = new Progress(userId, albumId, progressChoice, songCount, rating);
                                    boolean progressAddResult3 = pdao.addProgress(progressAdded3);
                                    if (progressAddResult3) {
                                        System.out.println(progressAdded3);
                                        System.out.println("Progress tracker successfully added");
                                    } else {
                                        System.out.println(message);
                                        throw new TrackingException(message);
                                    }
                                    stillChoosing = false;
                                    break;

                                default:

                                    stillChoosing = false;
                                    throw new TrackingException(message);

                                    //break;
                            }
                        }

                        break;

                    case 2:
                        // Update Progress
                        int userId2 = user.getUser_id();
                        System.out.println("What's the album id to update?");
                        System.out.println("----------------------------------------------------------------------------");

                        //RunnerController.viewAlbums(progList);
                        List<Progress> proggList = pdao.getAllUserTrackers(user.getUser_id());
                        Album alb = new Album();
                        int songsListened = 0;
                        int songsTotal = 0;
                        for (Progress p : proggList){
                            String albumName = adao.getAlbumNameById(p.getAlbum_id());
                            songsListened = p.getSong_count();
                            alb = adao.getAlbumId(p.getAlbum_id());
                            songsTotal = alb.getSongCount();
                            String prog = RunnerController.displayProgressBar(songsListened, songsTotal);


                            System.out.println(p.getAlbum_id() + " | " + String.format("%-38s", albumName) + " | "
                                    + String.format("%-12s", p.getProgress()) + " | " + p.getRating() + " | " +  prog);

                        }


                        System.out.println("\n");

                        int albumId2 = scan.nextInt();

                        songCount = 0;
                        rating = 0;

                        int choice2;
                        String progressChoice2;
                        String[] progressStatus2 = { "Not started", "In-progress", "Completed", "" };
                        RunnerController.progressUpdateMenu();

                        choice2 = scan.nextInt();

                        if(choice2 == 7){

                            System.out.println("How many songs have you listened to?");
                            songCount = scan.nextInt();
                            scan.nextLine();

                        }
                        if(choice2 == 8){
                            System.out.println("What would you rate the album out of 5?");
                            rating = scan.nextInt();
                            scan.nextLine();

                            Album a = adao.getAlbumId(albumId2);
                            songCount = a.getSongCount();


                        }

                        boolean stillChoosing2 = true;
                        while (stillChoosing2) {
                            switch (choice2) {
                                case 6:
                                    progressChoice2 = progressStatus2[0];
                                    //pdao.get
                                    Progress progressAdded = new Progress(userId2, albumId2, progressChoice2, songCount, rating);
                                    boolean progressAddResult = pdao.updateProgress(progressAdded);
                                    if (progressAddResult) {
                                        System.out.println(progressAdded);
                                        System.out.println("Progress tracker successfully updated");
                                    } else {
                                        System.out.println("Could not update progress tracker");
                                        throw new TrackingException();
                                    }
                                    stillChoosing2 = false;
                                    break;
                                case 7:
                                    progressChoice2 = progressStatus2[1];
                                    Progress progressAdded2 = new Progress(userId2, albumId2, progressChoice2, songCount, rating);
                                    boolean progressAddResult2 = pdao.updateProgress(progressAdded2);
                                    if (progressAddResult2) {
                                        System.out.println(progressAdded2);
                                        System.out.println("Progress tracker successfully updated");
                                    } else {
                                        System.out.println("Could not update progress tracker");
                                        throw new TrackingException();
                                    }
                                    stillChoosing2 = false;
                                    break;
                                case 8:
                                    progressChoice2 = progressStatus2[2];
                                    Progress progressAdded3 = new Progress(userId2, albumId2, progressChoice2, songCount, rating);

                                    boolean progressAddResult3 = pdao.updateProgress(progressAdded3);
                                    if (progressAddResult3) {
                                        System.out.println(progressAdded3);
                                        System.out.println("Progress tracker successfully updated");
                                    } else {
                                        System.out.println("Could not update progress tracker");
                                        throw new TrackingException();
                                    }
                                    stillChoosing2 = false;
                                    break;

                                default:
                                    System.out.println("Incorrect input");
                                    System.out.println("What's the status of the album to update?" + "\n"
                                            + "Please enter 6 for not started " + " 7 for in-progress or 8 for completed");
                                    stillChoosing2 = false;
                                    break;
                            }
                        }

                        break;

                    case 3:
                        // View Albums and their trackers
                        System.out.println("Your progress trackers and albums");
                        System.out.println("----------------------------------------------------------------------------------");

                        proggList = pdao.getAllUserTrackers(user.getUser_id());


                        alb = new Album();
                        songsListened = 0;
                        songsTotal = 0;
                        for (Progress p : proggList){
                            String albumName = adao.getAlbumNameById(p.getAlbum_id());
                            songsListened = p.getSong_count();
                            alb = adao.getAlbumId(p.getAlbum_id());
                            songsTotal = alb.getSongCount();
                            String prog = RunnerController.displayProgressBar(songsListened, songsTotal);




                            System.out.println(String.format("%-38s", albumName) + " | " 
                            + String.format("%-12s", p.getProgress()) + " | " + p.getRating() + " | " +  prog);

                        }
                        //RunnerController.viewAlbums(progList);

                        System.out.println("\n");

                        break;
                    case 4://get average ratings

                        List<Album> albAve = adao.getAllAlbums();

                        System.out.println("\n" + String.format("%-2s - %-38s   %-25s   Rating", "ID", "Album", "Artist"));


                        for (Album a : albAve){
                            Double ave = pdao.getAveRating(a.getAlbum_id());
                            System.out.println(String.format("%-2s", a.getAlbum_id()) + 
                            		" | " + String.format("%-38s", a.getAlbumName()) + " | " 
                            		+ String.format("%-25s", a.getArtist()) + " | " + ave);

                        }
                        break;

                    case 5:
                        System.out.println("You have logged out\n");
                        break;
                    case 6:
                        RunnerController.addAlbum(scan, adao);
                        break;
                    case 7:
                       RunnerController.updateAlbum(scan);
                        break;

                    default:
                        System.out.println("Invalid input, try again!\n");
                        scan.nextLine();
                }
            } while (ans != 5);
            // System.exit(0);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input, must enter a number");
        } catch (TrackingException e) {

        } catch (NoSuchElementException e) {
            System.out.println("Input was not recognized");
        }

    }


//    public static void menuDisplay() {
//        System.out.println("+====================================+");
//        System.out.println("              TRACKING APP");
//        System.out.println("+====================================+");
//        System.out.println("\n1. Login\n2. Register\n0. Quit\n");
//    }
//    
//    
//    public static void menu(User user) {
//
//        System.out.println("\n+============================================+");
//        System.out.println("  Hello, " + user.getUsername());
//        System.out.println("| Welcome to the Album Progress Tracker!     |");
//        System.out.println("|                                            |");
//        System.out.println("| Please choose from the following options:  |");
//        System.out.println("|                                            |");
//        System.out.println("| 1: Add Album                               |");
//        System.out.println("| 2: Add Progress                            |");
//        System.out.println("| 3: Update Progress                         |");
//        System.out.println("| 4: List Albums                             |");
//        System.out.println("| 5: LOGOUT                                  |");
//        System.out.println("|                                            |");
//        System.out.println("+============================================+");
//    }
//    public static void progressMenu() {
//
//        System.out.println("Please choose your progress:			  ");
//        System.out.println("                                          ");
//        System.out.println("6 - Not Started                           ");
//        System.out.println("7 - In Progress                           ");
//        System.out.println("8 - Completed                            \n");
//    }
//    public static void progressUpdateMenu() {
//
//        System.out.println("Please choose your updated progress:      ");
//        System.out.println("                                          ");
//        System.out.println("6 - Not Started                           ");
//        System.out.println("7 - In Progress                           ");
//        System.out.println("8 - Completed                            \n");
//    }
//
//    public static void addAlbum(Scanner scan, AlbumDaoSQL albumCaller) {
//        System.out.println("What's the name of the new album?");
//        String testVar = scan.next();
//        String albumName = testVar + scan.nextLine();
//
//        Album albumAdded = new Album(albumName);
//        boolean addResult = albumCaller.addAlbum(albumAdded);
//        if (addResult) {
//            System.out.println(albumAdded);
//            System.out.println("Album successfully added");
//        } else {
//            System.out.println("Could not add album");
//        }
//    }
//
//    public static void viewAlbums(List<Progress> progList) {
//
//        AlbumDaoSQL albumCaller = new AlbumDaoSQL();
//        if (progList.isEmpty()) {
//            System.out.println("\nYou aren't tracking any albums.\n");
//        }
//        List<Album> albums = albumCaller.getAllAlbums();
//        progList.forEach(progress -> {
//
//            Album progressAlbum =
//                    albums.stream().filter(album -> album.getAlbum_id() == progress.getAlbum_id())
//                            .findFirst().get();
//            if (progressAlbum.getAlbum_id() < 10)	System.out.printf("\n %s - %s -> %s", progressAlbum.getAlbum_id(),
//                    progressAlbum.getAlbum(), progress.getProgress());
//            else System.out.printf("\n%s - %s -> %s", progressAlbum.getAlbum_id(),
//                    progressAlbum.getAlbum(), progress.getProgress());
//        });
//    }

}
