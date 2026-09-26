package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.model.Album;

import java.util.List;

public interface AlbumRepo {

    List<Album> getAllAlbums();

    Album getAlbumById(int id);

    List<Album> searchAlbum(String keyword);

    List<Album> getAllArchivedAlbums();

    List<Album> getAlbumsByArtistId(int artistId);

    boolean createAlbum(Album album);

    boolean updateAlbum(Album album);

    boolean archiveAlbum(int id);

    boolean restoreAlbum(int id);

    boolean deleteAlbum(int id);
}