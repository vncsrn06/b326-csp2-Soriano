package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.dao.AlbumDao;
import com.joysistvi.recordingapp.model.Album;

import java.util.List;

public class AlbumServiceImpl implements AlbumService {

    private final AlbumDao albumDao;

    public AlbumServiceImpl(AlbumDao albumDao) {
        this.albumDao = albumDao;
    }

    @Override
    public List<Album> getAllAlbums() {
        return albumDao.getAllAlbums();
    }

    @Override
    public List<Album> getAlbumsByArtistId(int artistId) {
        return albumDao.getAlbumsByArtistId(artistId);
    }

    @Override
    public Album getAlbumById(int id) {
        return albumDao.getAlbumById(id);
    }

    @Override
    public List<Album> searchAlbum(String keyword) {
        return albumDao.searchAlbum(keyword);
    }

    @Override
    public List<Album> getAllArchivedAlbums() {
        return albumDao.getAllArchivedAlbums();
    }

    @Override
    public boolean createAlbum(Album album) {
        return albumDao.createAlbum(album);
    }

    @Override
    public boolean updateAlbum(Album album) {
        return albumDao.updateAlbum(album);
    }

    @Override
    public boolean archiveAlbum(int id) {
        return albumDao.archiveAlbum(id);
    }

    @Override
    public boolean restoreAlbum(int id) {
        return albumDao.restoreAlbum(id);
    }

    @Override
    public boolean deleteAlbum(int id) {
        return albumDao.deleteAlbum(id);
    }
}
