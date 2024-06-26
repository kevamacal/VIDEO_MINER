package aiss.videominer.controller;

import aiss.videominer.exception.CommentNotFoundException;
import aiss.videominer.exception.MinValueException;
import aiss.videominer.model.Channel;
import aiss.videominer.model.Comment;
import aiss.videominer.repository.CommentRepository;
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
    public List<Comment> findAll(@Parameter(description = "number of the page to be retrieved")@RequestParam(defaultValue = "0")int page,
                                 @Parameter(description = "size of page to be retrieved")@RequestParam(defaultValue = "10")int size,
                                 @Parameter(description = "parameters to be retrieved")@RequestParam(required = false) String name,
                                 @Parameter(description = "parameter to order comments retrieved")@RequestParam(required = false)String order) throws MinValueException {

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

        Page<Comment> pageComment;

        if(name != null){
            pageComment = repository.findById(name,paging);
        }else {
            pageComment = repository.findAll(paging);
        }

        return pageComment.getContent();
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
    public Comment findOne(@PathVariable String id) throws CommentNotFoundException {
        Optional<Comment> comment = repository.findById(id);
        return comment.get();
    }
}