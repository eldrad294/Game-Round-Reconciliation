package com.example.gameroundreconciliation.jpa.bet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BetImpl implements BetService {

    private final BetRepository betRepository;

    public BetImpl(BetRepository betRepository){
        this.betRepository = betRepository;
    }

    public void upsertBet(Bet bet){

        if (bet.getGameInstanceId() == null)
            throw new IllegalArgumentException("Received bet with an empty GameInstanceId!");

        Optional<Bet> result = this.betRepository.findById(bet.getGameInstanceId());
        if (result.isEmpty()){
            this.betRepository.save(bet);
            log.info("Inserting bet -> {}", bet);
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
            updatedBet.setId(bet.getId());

            this.betRepository.save(updatedBet);
        }

    }

    @Override
    public List<Bet> getBets() {
        return this.betRepository.findAll();
    }

    @Override
    public Bet getBet(Long gameInstanceId) {
        return this.betRepository.getById(gameInstanceId);
    }
}
