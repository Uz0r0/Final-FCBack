package com.example.FiteClub.models;

import com.example.FiteClub.Category;
import com.example.FiteClub.dto.CardDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Entity
@Table(name = "duel")
@Getter
@Setter
public class Duel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "card1_card_id")
    private Card card1;
    @ManyToOne
    @JoinColumn(name = "card2_card_id")
    private Card card2;

    public boolean shown = false;

    public void setShown(boolean Shown) {
        this.shown = Shown;
    }
    public boolean getShown() {
        return shown;
    }


}
