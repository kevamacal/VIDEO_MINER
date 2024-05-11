package aiss.videominer.controller;

import aiss.videominer.repository.ChannelRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import aiss.videominer.model.Channel;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/videominer")
public class ChannelController {

    @Autowired
    ChannelRepository repository;

    @GetMapping
    public List<Channel> findAll(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Channel findOne(@PathVariable Long id){
        Optional<Channel> channel = repository.findById(id);
        return channel.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/channels")
    public Channel create(@Valid @RequestBody Channel channel){
        Channel newChannel = repository.save(
                new Channel(channel.getId(),
                        channel.getName(),
                        channel.getDescription(),
                        channel.getCreatedTime(),
                        channel.getVideos())
        );
        return newChannel;
    }
}
