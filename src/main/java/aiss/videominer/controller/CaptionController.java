package aiss.videominer.controller;

import aiss.videominer.model.Caption;
import aiss.videominer.repository.CaptionRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Caption",description = "Caption management API")
@RestController
@RequestMapping("/videominer/captions")
public class CaptionController {
    @Autowired
    CaptionRepository repository;

    @Operation(
            summary="Retrieve a list of captions",
            description = "Get a list of captions",
            tags = {"captions", "get"})

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Caption.class), mediaType = "application/json")}),
    })
    @GetMapping
    public List<Caption> findAll(){
        return repository.findAll();
    }
    @Operation(
            summary="Retrieve a caption by Id",
            description = "Get a caption by specifying its id",
            tags = {"captions", "get"})

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Caption.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema)})
    })
    @GetMapping("/{id}")
    public Caption findOne(@PathVariable String id){
        Optional<Caption> channel = repository.findById(id);
        return channel.get();
    }
}