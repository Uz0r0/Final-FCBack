package com.example.FiteClub.dto;

import com.example.FiteClub.Category;
import com.example.FiteClub.models.Card;
import lombok.Getter;

@Getter
public class DuelDTO {
    private long id;
    private Category category;
    private CardDTO card1;
    private CardDTO card2;

    public DuelDTO() {}
    public DuelDTO(long id, Category category, Card card1, Card card2) {
        this.id = id;
        this.category = category;
        this.card1 = new CardDTO(card1);
        this.card2 = new CardDTO(card2);
    }
    public DuelDTO(long id, Category category, CardDTO card1, CardDTO card2) {
        this.id = id;
        this.category = category;
        this.card1 = card1;
        this.card2 = card2;
    }
}
