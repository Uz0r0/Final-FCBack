package com.example.FiteClub.Services;

import com.example.FiteClub.Security.Repositories.UserRepository;
import com.example.FiteClub.Security.UserPackage.UserEntity;
import com.example.FiteClub.models.Card;
import com.example.FiteClub.models.Duel;
import com.example.FiteClub.repos.CardRepo;
import com.example.FiteClub.repos.DuelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class CardService {
    @Autowired
    private CardRepo cardRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private DuelRepo duelRepo;


    public void like(Card card) {
        card.setLikes(card.getLikes() + 1);
        cardRepo.save(card);
    }
    public ResponseEntity<String> delete(Principal principal){
        String username = principal.getName();
        UserEntity user = userRepo.findByUsername(username).orElseThrow(()->new RuntimeException("User not found"));
        if (user.getCard() == null) {
            throw new RuntimeException("User does not have a card");
        }


        Card card = cardRepo.findById(user.getCard().getId()).orElseThrow(()->new RuntimeException("Card not found"));
        List<Duel> duels = duelRepo.findAllByCard1_IdOrCard2_Id(card.getId(), card.getId());
//        if(duels == null || duels.size() == 0 || card == null){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card or duels not found");
//        }
        System.out.println(duels);
        duelRepo.deleteAll(duels);
        user.setCard(null);
        userRepo.save(user);
        cardRepo.delete(card);
        System.out.println("Card deleted successfully");
        return ResponseEntity.ok("Card and all related duels were deleted successfully");
    }
}
