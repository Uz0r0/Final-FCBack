package com.example.FiteClub.dto;

import com.example.FiteClub.models.Card;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardDTO {
    private String music;
    private String musician;
    private String actor;
    private String anime;
    private String film;
    private String meme;
    private long id;
    private long likes;
    private String username;


    public CardDTO(String music, String musician, String actor, String anime, String film, String meme,long id, long likes, String username) {
        this.music = music;
        this.musician = musician;
        this.actor = actor;
        this.anime = anime;
        this.film = film;
        this.meme = meme;
        this.id = id;
        this.likes = likes;
        this.username = username;
    }
    public CardDTO() {}
    public CardDTO(Card card){
        this.music = card.getMusic();
        this.musician = card.getMusician();
        this.actor = card.getActor();
        this.anime = card.getAnime();
        this.film = card.getFilm();
        this.meme = card.getMeme();
        this.id = card.getId();
        this.likes = card.getLikes();
        this.username=card.getUser().getUsername();
    }

}
