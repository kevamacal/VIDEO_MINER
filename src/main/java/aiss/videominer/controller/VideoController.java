package aiss.videominer.controller;

import aiss.videominer.exception.MinValueException;
import aiss.videominer.exception.VideoNotFoundException;
import aiss.videominer.model.Channel;
import aiss.videominer.model.Comment;
import aiss.videominer.model.Video;
import aiss.videominer.repository.VideoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public List<Video> findAll(@Parameter(description = "number of the page to be retrieved")@RequestParam(defaultValue = "0")int page,
                                 @Parameter(description = "size of page to be retrieved")@RequestParam(defaultValue = "10")int size,
                                 @Parameter(description = "parameters to be retrieved")@RequestParam(required = false) String name,
                                 @Parameter(description = "parameter to order videos retrieved")@RequestParam(required = false)String order) throws MinValueException {

        if (page < 0 || size < 0){
            throw new MinValueException();
        }

        Pageable paging;

        if(order != null){
            if (order.startsWith("-")){
                paging = PageRequest.of(page,size, Sort.by(order.substring(1)).descending());
            }else {
                paging = PageRequest.of(page,size, Sort.by(order).ascending());
            }
        }else{
            paging = PageRequest.of(page,size);
        }

        Page<Video> pageVideo;

        if(name != null){
            pageVideo = repository.findByName(name,paging);
        }else {
            pageVideo = repository.findAll(paging);
        }

        return pageVideo.getContent();
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
    public Video findOne(@PathVariable String id) throws VideoNotFoundException {
        Optional<Video> channel = repository.findById(id);
        return channel.get();
    }
}