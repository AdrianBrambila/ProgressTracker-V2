package com.jumpplus.tracker.dao;

import com.jumpplus.tracker.model.Album;

import java.util.List;

public interface AlbumDao {
    public Album getAlbumId(int a_id);

    public List<Album> getAllAlbums();

    public boolean addAlbum(Album album);



}
