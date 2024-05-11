package aiss.videominer.controller;

import aiss.videominer.model.Comment;
import aiss.videominer.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/videominer")
public class CommentController {

    @Autowired
    CommentRepository repository;

    @GetMapping
    public List<Comment> findAll(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Comment findOne(@PathVariable Long id){
        Optional<Comment> comment = repository.findById(id);
        return comment.get();
    }
}
