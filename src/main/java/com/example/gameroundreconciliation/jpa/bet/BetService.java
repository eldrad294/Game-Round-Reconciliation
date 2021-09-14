package com.example.gameroundreconciliation.jpa.bet;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BetService {

    void upsertBet(Bet bet);

    List<Bet> getBets();

    Bet getBet(Long gameInstanceId);

}