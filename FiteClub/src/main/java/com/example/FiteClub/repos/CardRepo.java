package com.example.FiteClub.repos;

import com.example.FiteClub.models.Card;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CardRepo extends CrudRepository<Card, Long> {
    Card findById(long id);
    String findUsernameById(long id);


    void delete(Card card);
    @Query(value = """
        SELECT * FROM cards
        WHERE 
          (:category = 'MUSIC' AND music IS NOT NULL) OR
          (:category = 'MUSICIAN' AND musician IS NOT NULL) OR
          (:category = 'ACTOR' AND actor IS NOT NULL) OR
          (:category = 'ANIME' AND anime IS NOT NULL) OR
          (:category = 'FILM' AND film IS NOT NULL) OR
          (:category = 'MEME' AND meme IS NOT NULL)
        ORDER BY RANDOM() LIMIT 1
        """, nativeQuery = true)
    Card findRandomCardByCategory(@Param("category") String category);

}
