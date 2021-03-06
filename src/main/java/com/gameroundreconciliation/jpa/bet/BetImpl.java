package com.gameroundreconciliation.jpa.bet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BetImpl implements BetService {

    private final BetRepository betRepository;

    /**
     * Class Constructor
     * @param betRepository - {@link BetRepository}
     * */
    public BetImpl(BetRepository betRepository){
        this.betRepository = betRepository;
    }

    /**
     * Upsert service, which achieves database upsertion and deduplication of bets based off the GAME_INSTANCE_ID
     * dimension.
     * @param bet - {@link Bet}
     * */
    public void upsertBet(Bet bet){

        if (bet.getGameInstanceId() == null)
            throw new IllegalArgumentException("Received bet with an empty GameInstanceId!");

        Optional<Bet> result = this.betRepository.findById(bet.getGameInstanceId());
        if (result.isEmpty()){
            this.betRepository.save(bet);
            log.info("Inserted bet -> {}", bet);
        }else{
            Bet updatedBet = result.get();

            updatedBet.setBonusAmountBet(bet.getBonusAmountBet());
            updatedBet.setBonusAmountWin(bet.getBonusAmountWin());
            updatedBet.setCreatedTimestamp(bet.getCreatedTimestamp());
            updatedBet.setGameId(bet.getGameId());
            updatedBet.setProvider(bet.getProvider());
            updatedBet.setRealAmountBet(bet.getRealAmountBet());
            updatedBet.setGameName(bet.getGameName());
            updatedBet.setUserId(bet.getUserId());
            updatedBet.setRealAmountWin(bet.getRealAmountWin());

            this.betRepository.save(updatedBet);
            log.info("Updated bet -> {}", updatedBet);
        }

    }

    /**
     * Retrieves list of raw bets from database.
     * @return {@link List} of {@link Bet}
     * */
    @Override
    public List<Bet> getBets() {
        return this.betRepository.findAll();
    }

    /**
     * Retrieves single record based off the GAME_INSTANCE_ID.
     * @param gameInstanceId - {@link Long}
     * @return {@link Bet}
     * */
    @Override
    public Bet getBet(Long gameInstanceId) {
        return this.betRepository.getById(gameInstanceId);
    }
}
