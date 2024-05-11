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


@RestController
@RequestMapping("/videominer/channels")
public class ChannelController {

    @Autowired
    ChannelRepository repository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Channel create(@Valid @RequestBody Channel channel ){
        Channel _channel = repository.save(new Channel(channel.getId(),channel.getName(), channel.getDescription(), channel.getCreatedTime(), channel.getVideos()));
        return _channel;
    }

}
