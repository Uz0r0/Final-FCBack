package com.example.FiteClub.controllers;

import com.example.FiteClub.Security.Repositories.UserRepository;
import com.example.FiteClub.Security.UserPackage.UserEntity;
import com.example.FiteClub.Services.CardService;
import com.example.FiteClub.dto.CardDTO;
import com.example.FiteClub.models.Card;
import com.example.FiteClub.models.Duel;
import com.example.FiteClub.repos.CardRepo;
import com.example.FiteClub.repos.DuelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {
    @Autowired
    private CardRepo cardRepo;

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private CardService cardService;
    @Autowired
    private DuelRepo duelRepo;

    @Transactional
    @PostMapping("/add")
    public CardDTO postCard(@RequestBody CardDTO cardDTO, Principal principal) {
        String username = principal.getName();
        UserEntity user = userRepo.findByUsername(username).orElseThrow(()->new RuntimeException("User not found"));

        if(user.getCard() != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Card already exists");
        } else {
            Card card = new Card();
            card.setMusic(cardDTO.getMusic());
            card.setMusician(cardDTO.getMusician());
            card.setActor(cardDTO.getActor());
            card.setAnime(cardDTO.getAnime());
            card.setFilm(cardDTO.getFilm());
            card.setMeme(cardDTO.getMeme());
            card.setUser(user);
            user.setCard(card);

            userRepo.save(user);
            return cardDTO;
        }

    }

    @Transactional
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCard(Principal principal) {
        return cardService.delete(principal);
    }

    @GetMapping("/get-cardData")
    public CardDTO getCardData(Principal principal) {
        String username = principal.getName();
        UserEntity user = userRepo.findByUsername(username).orElseThrow(()->new RuntimeException("User not found"));
        if (user.getCard() == null) {
            throw new RuntimeException("User does not have a card");
        }

        Card card = cardRepo.findById(user.getCard().getId()).orElseThrow(()->new RuntimeException("Card not found"));
        CardDTO cardDTO = new CardDTO();
        cardDTO.setMusic(card.getMusic());
        cardDTO.setMusician(card.getMusician());
        cardDTO.setActor(card.getActor());
        cardDTO.setAnime(card.getAnime());
        cardDTO.setFilm(card.getFilm());
        cardDTO.setMeme(card.getMeme());
        return cardDTO;
    }
    @Transactional
    @PutMapping("/change")
    public ResponseEntity<String> changeCard(Principal principal, @RequestBody CardDTO cardDTO) {
        String username = principal.getName();
        UserEntity user = userRepo.findByUsername(username).orElseThrow(()->new RuntimeException("User not found"));
        if (user.getCard() == null) {
            throw new RuntimeException("User does not have a card");
        }

        Card card = cardRepo.findById(user.getCard().getId()).orElseThrow(()->new RuntimeException("Card not found"));
        card.setMusic(cardDTO.getMusic());
        card.setMusician(cardDTO.getMusician());
        card.setActor(cardDTO.getActor());
        card.setAnime(cardDTO.getAnime());
        card.setFilm(cardDTO.getFilm());
        card.setMeme(cardDTO.getMeme());
        cardRepo.save(card);
        System.out.println("Card changed successfully");
        return ResponseEntity.ok("Card changed successfully");
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> likeCard(Principal principal, @PathVariable long id) {
        UserEntity user = userRepo.findByUsername(principal.getName()).orElseThrow(()->new RuntimeException("User not found"));
        if (user.getCard() == null) {
            throw new RuntimeException("User does not have a card");
        }

        Card card;
        if (cardRepo.existsById(id)) {
            card = cardRepo.findById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found");
        }
        cardService.like(card);
        System.out.println("Card like successfully"+ card.getLikes());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getID")
    public long getCardId(Card card){
        return card.getId();
    }
}
