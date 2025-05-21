package com.example.FiteClub.models;

import com.example.FiteClub.Security.UserPackage.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.function.Function;

@Entity
@Getter
@Setter
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String music;
    private String musician;
    private String actor;
    private String anime;
    private String film;
    private String meme;

    private long likes;

    public Card() {}

    public Card(String music, String musician, String actor, String anime, String film, String meme) {
        this.music = music;
        this.musician = musician;
        this.actor = actor;
        this.anime = anime;
        this.film = film;
        this.meme = meme;
    }

    public static String getLineByCategory(Card card, String category) {
        Map<String, Function<Card, String>> getters = Map.of(
                "MUSIC", Card::getMusic,
                "MUSICIAN", Card::getMusician,
                "ACTOR", Card::getActor,
                "ANIME", Card::getAnime,
                "FILM", Card::getFilm,
                "MEME", Card::getMeme
        );
        Function<Card, String> getter = getters.get(category);
        if (getter == null) {
            return "Invalid(Alisher) category: " + category;
        }
        return getter.apply(card);
    }



    public void setMusic(String music) {
        this.music = music;
    }

    public String getMusician() {
        return musician;
    }

    public void setMusician(String musician) {
        this.musician = musician;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getAnime() {
        return anime;
    }

    public void setAnime(String anime) {
        this.anime = anime;
    }

    public String getFilm() {
        return film;
    }

    public void setFilm(String film) {
        this.film = film;
    }

    public String getMeme() {
        return meme;
    }

    public void setMeme(String meme) {
        this.meme = meme;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }
}
