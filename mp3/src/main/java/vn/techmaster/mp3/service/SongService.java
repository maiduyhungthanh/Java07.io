package vn.techmaster.mp3.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import vn.techmaster.mp3.model.Song;
import vn.techmaster.mp3.repository.SongRepo;

public class SongService {
    @Autowired
    SongRepo songRepo;

    public Collection<Song> search(String search){
       return songRepo.findAll()
       .stream()
       .filter(song->song.getName().toLowerCase().contains(search.toLowerCase())
       &&song.getSinger().toLowerCase().contains(search.toLowerCase()))
       .collect(Collectors.toList());
     

    }
}
