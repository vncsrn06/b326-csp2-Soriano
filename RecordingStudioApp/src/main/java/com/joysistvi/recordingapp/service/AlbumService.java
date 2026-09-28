package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Album;

import java.util.List;

public interface AlbumService {

    List<Album> getAllAlbums();

    List<Album> getAlbumsByArtistId(int artistId);

    Album getAlbumById(int id);

    List<Album> searchAlbum(String keyword);

    List<Album> getAllArchivedAlbums();

    boolean createAlbum(Album album);

    boolean updateAlbum(Album album);

    boolean archiveAlbum(int id);

    boolean restoreAlbum(int id);

    boolean deleteAlbum(int id);
}
