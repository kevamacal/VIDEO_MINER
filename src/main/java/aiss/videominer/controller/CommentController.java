package aiss.videominer.controller;

import aiss.videominer.model.Comment;
import aiss.videominer.repository.CommentRepository;
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

@Tag(name = "Comments",description = "Comment management API")
@RestController
@RequestMapping("/videominer/comments")
public class CommentController {

    @Autowired
    CommentRepository repository;

    @Operation(
            summary = "Retrieve a list of comments",
            description = "Get a list of comments",
            tags = {"comments","get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",content = {@Content(schema = @Schema(implementation = Comment.class),mediaType = "application/json")})
    })
    @GetMapping
    public List<Comment> findAll(){
        return repository.findAll();
    }

    @Operation(
            summary="Retrieve a comment by Id",
            description = "Get a comment by specifying its id",
            tags = {"comments", "get"})

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Comment.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema)})
    })
    @GetMapping("/{id}")
    public Comment findOne(@PathVariable String id){
        Optional<Comment> comment = repository.findById(id);
        return comment.get();
    }
}