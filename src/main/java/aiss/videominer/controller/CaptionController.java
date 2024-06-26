package aiss.videominer.controller;

import aiss.videominer.exception.CaptionNotFoundException;
import aiss.videominer.exception.MinValueException;
import aiss.videominer.model.Caption;
import aiss.videominer.repository.CaptionRepository;
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
    public List<Caption> findAll(@Parameter(description = "number of the page to be retrieved")@RequestParam(defaultValue = "0")int page,
                                 @Parameter(description = "size of page to be retrieved")@RequestParam(defaultValue = "10")int size,
                                 @Parameter(description = "parameters to be retrieved")@RequestParam(required = false) String name,
                                 @Parameter(description = "parameter to order captions retrieved")@RequestParam(required = false)String order) throws MinValueException {

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

        Page<Caption> pageCaption;

        if(name != null){
            pageCaption = repository.findByName(name,paging);
        }else {
            pageCaption = repository.findAll(paging);
        }

        return pageCaption.getContent();
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
    public Caption findOne(@PathVariable String id) throws CaptionNotFoundException {
        Optional<Caption> Caption = repository.findById(id);
        return Caption.get();
    }
}