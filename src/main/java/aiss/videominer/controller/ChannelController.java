package aiss.videominer.controller;

import aiss.videominer.exception.ChannelNotFoundException;
import aiss.videominer.repository.ChannelRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import aiss.videominer.model.Channel;

import java.util.List;
import java.util.Optional;

@Tag(name = "Channel", description = "Channel management API")
@RestController
@RequestMapping("/videominer/channels")
public class ChannelController {

    @Autowired
    ChannelRepository repository;
    @Operation(
        summary = "Retrieve a list of channels",
        description = "Get a list of channels",
        tags = {"channels","get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Channel.class),mediaType = "application/json")})
    })
   @GetMapping
    public List<Channel> findAll(){
        return repository.findAll();
    }

    @Operation(
            summary = "Retrieve a channel by id",
            description = "Get a Channel object by specifying its id",
            tags = {"channels","get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Channel.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/{id}")
    public Channel findOne(@Parameter(description = "id of the channel to be searched") @PathVariable String id) throws ChannelNotFoundException {
        Optional<Channel> channel = repository.findById(id);
        return channel.get();
    }
    @Operation(
            summary = "Insert a channel",
            description = "Add a new Channel whose data is passed in the body of the request in JSON format",
            tags = {"channels","post"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = Channel.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }) })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Channel create(@Valid @RequestBody Channel channel ){
        Channel _channel = repository.save(new Channel(channel.getId(),channel.getName(), channel.getDescription(), channel.getCreatedTime(), channel.getVideos()));
        return _channel;
    }
}