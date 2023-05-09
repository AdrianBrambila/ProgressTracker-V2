package com.jumpplus.tracker.dao;

import com.jumpplus.tracker.model.Album;

import java.util.List;

public interface AlbumDao {
    public Album getAlbumId(int a_id);
    public String getAlbumNameById(int a_id);

    public List<Album> getAllAlbums();
    public List<String> getAllArtists();
    public List<String> getAllGenres();

    public boolean addArtist(String artist);
    public boolean addGenre(String genre);

    public boolean addAlbum(Album album, int artist_id, int genre_id);

    public boolean updateAlbum(Album album, int artist_id, int genre_id);

    public int getArtistId(String artist);
    public int getGenreId(String genre);






}
