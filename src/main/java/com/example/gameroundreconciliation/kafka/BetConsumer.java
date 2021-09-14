package com.example.gameroundreconciliation.kafka;

import com.example.gameroundreconciliation.jpa.bet.Bet;
import com.example.gameroundreconciliation.jpa.bet.BetImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BetConsumer {

    private final BetImpl betImpl;

    public BetConsumer(BetImpl betImpl){
        this.betImpl = betImpl;
    }

    @KafkaListener(topics = "staging-bets", groupId = "group_id")
    public void consume(Bet bet) throws InterruptedException {
        log.debug("Consumed bet -> {}", bet.toString());
        this.betImpl.upsertBet(bet);
    }
}