package com.example.FiteClub.repos;

import com.example.FiteClub.models.Duel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DuelRepo extends CrudRepository<Duel, Long> {
    @Query(value = "SELECT * FROM duel ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Duel getRandomDuel();

    Duel findFirstByShownFalse();

    List<Duel> findAllByShownFalse();

    @Query(value = "SELECT * FROM duel WHERE shown = false ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Duel findRandomUnshownDuel();
    Duel findById(long id);
    List<Duel> findAllById(long id);
    List<Duel> findAllByCard1_IdOrCard2_Id(long id1, long id2);
}
