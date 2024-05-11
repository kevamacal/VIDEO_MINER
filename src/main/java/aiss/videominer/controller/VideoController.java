package aiss.videominer.controller;

import aiss.videominer.model.Video;
import aiss.videominer.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public class VideoController {
    @Autowired
    VideoRepository repository;

    @GetMapping
    public List<Video> findAll(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Video findOne(@PathVariable Long id){
        Optional<Video> channel = repository.findById(id);
        return channel.get();
    }
}
