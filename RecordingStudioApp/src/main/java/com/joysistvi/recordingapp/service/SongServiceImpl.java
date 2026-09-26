package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Song;
import com.joysistvi.recordingapp.repository.SongRepo;

import java.util.List;

public class SongServiceImpl implements SongService {

    private final SongRepo songRepo;

    public SongServiceImpl(SongRepo songRepo) {
        this.songRepo = songRepo;
    }

    @Override
    public List<Song> getAllSongs() {
        return songRepo.getAllSongs();
    }

    @Override
    public List<Song> getSongsByAlbumId(int albumId) {
        return songRepo.getSongsByAlbumId(albumId);
    }

    @Override
    public Song getSongById(int id) {
        return songRepo.getSongById(id);
    }

    @Override
    public List<Song> searchSong(String keyword) {
        return songRepo.searchSong(keyword);
    }

    @Override
    public boolean createSong(Song song) {
        return songRepo.createSong(song);
    }

    @Override
    public boolean updateSong(Song song) {
        return songRepo.updateSong(song);
    }

    @Override
    public boolean deleteSong(int id) {
        return songRepo.deleteSong(id);
    }
}
