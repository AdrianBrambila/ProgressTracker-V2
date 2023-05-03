package com.jumpplus.tracker.model;

public class Album {
    private int album_id;
    private String albumName;

    private String artist;
    private String genre;
    private int releaseYear;



    public Album(){

    };
    public Album(int album_id, String albumName, String artist, String genre, int releaseYear) {
        this.album_id = album_id;
        this.albumName = albumName;
        this.artist = artist;
        this.genre = genre;
        this.releaseYear = releaseYear;
    }

    /**
     * @return the album_id
     */
    public int getAlbum_id() {
        return album_id;
    }

    /**
     * @param album_id the album_id to set
     */
    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }
    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getReleaseYear() {
        return releaseYear;
    }


    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }


    @Override
    public String toString() {
        return "Album{" +
                "album_id=" + album_id +
                ", albumName='" + albumName + '\'' +
                ", artist='" + artist + '\'' +
                ", genre='" + genre + '\'' +
                ", releaseYear=" + releaseYear +
                '}';
    }
}
