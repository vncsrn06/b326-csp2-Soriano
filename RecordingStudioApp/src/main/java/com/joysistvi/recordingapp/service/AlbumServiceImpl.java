package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Album;
import com.joysistvi.recordingapp.repository.AlbumRepo;

import java.util.List;

public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepo albumRepo;

    public AlbumServiceImpl(AlbumRepo albumRepo) {
        this.albumRepo = albumRepo;
    }

    @Override
    public List<Album> getAllAlbums() {
        return albumRepo.getAllAlbums();
    }

    @Override
    public Album getAlbumById(int id) {
        return albumRepo.getAlbumById(id);
    }

    @Override
    public List<Album> searchAlbum(String keyword) {
        return albumRepo.searchAlbum(keyword);
    }

    @Override
    public List<Album> getAllArchivedAlbums() {
        return albumRepo.getAllArchivedAlbums();
    }

    @Override
    public List<Album> getAlbumsByArtistId(int artistId) {
        return albumRepo.getAlbumsByArtistId(artistId);
    }

    @Override
    public boolean createAlbum(Album album) {
        return albumRepo.createAlbum(album);
    }

    @Override
    public boolean updateAlbum(Album album) {
        return albumRepo.updateAlbum(album);
    }

    @Override
    public boolean archiveAlbum(int id) {
        return albumRepo.archiveAlbum(id);
    }

    @Override
    public boolean restoreAlbum(int id) {
        return albumRepo.restoreAlbum(id);
    }

    @Override
    public boolean deleteAlbum(int id) {
        return albumRepo.deleteAlbum(id);
    }
}
