package com.gameroundreconciliation.jpa.bet;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BetService {

    /**
     * Upsert service, which achieves database upsertion and deduplication of bets based off the GAME_INSTANCE_ID
     * dimension.
     * @param bet - {@link Bet}
     * */
    void upsertBet(Bet bet);

    /**
     * Retrieves list of raw bets from database.
     * @return {@link List} of {@link Bet}
     * */
    List<Bet> getBets();

    /**
     * Retrieves single record based off the GAME_INSTANCE_ID.
     * @param gameInstanceId - {@link Long}
     * @return {@link Bet}
     * */
    Bet getBet(Long gameInstanceId);

}