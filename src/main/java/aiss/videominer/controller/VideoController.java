package aiss.videominer.controller;

import aiss.videominer.model.Video;
import aiss.videominer.repository.VideoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Tag(name = "Video",description = "Video management API")
@RestController
@RequestMapping("/videominer/videos")
public class VideoController {
    @Autowired
    VideoRepository repository;

    @Operation(
            summary="Retrieve a list of videos",
            description = "Get a list of videos",
            tags = {"videos", "get"})

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Video.class), mediaType = "application/json")}),
    })
    @GetMapping
    public List<Video> findAll(){
        return repository.findAll();
    }

    @Operation(
            summary="Retrieve a video by Id",
            description = "Get a video by specifying its id",
            tags = {"videos", "get"})

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Video.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema)})
    })
    @GetMapping("/{id}")
    public Video findOne(@PathVariable String id){
        Optional<Video> channel = repository.findById(id);
        return channel.get();
    }
}