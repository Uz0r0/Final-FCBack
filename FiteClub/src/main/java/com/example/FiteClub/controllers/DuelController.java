package com.example.FiteClub.controllers;

import com.example.FiteClub.Services.DuelService;
import com.example.FiteClub.dto.DuelDTO;
import com.example.FiteClub.dto.DuelResponseDTO;
import com.example.FiteClub.models.Duel;
import com.example.FiteClub.repos.DuelRepo;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/fite")
public class DuelController {
    @Autowired
    private DuelService duelService;

    @Autowired
    private DuelRepo duelRepo;

    @GetMapping("/duel")
    public ResponseEntity<Object> duel(){
        return duelService.getOrCreateDuel();
    }

    @PostMapping(value = "/duel-create", produces = "application/json")
    public ResponseEntity<Duel> createDuel() {
        return ResponseEntity.ok(duelService.createDuel());
    }

//    @GetMapping("/duel-see")
//    public ResponseEntity<DuelResponseDTO> getDuel() {
//        return duelService.seeDuel();
//    }
}
