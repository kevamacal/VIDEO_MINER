package aiss.videominer.controller;

import aiss.videominer.model.Caption;
import aiss.videominer.repository.CaptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/videominer")
public class CaptionController {
    @Autowired
    CaptionRepository repository;

    @GetMapping
    public List<Caption> findAll(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Caption findOne(@PathVariable Long id){
        Optional<Caption> channel = repository.findById(id);
        return channel.get();
    }
}
