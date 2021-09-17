package com.gameroundreconciliation.kafka;

import com.gameroundreconciliation.jpa.aggbet.AggBet;
import com.gameroundreconciliation.jpa.aggbet.AggBetImpl;
import com.gameroundreconciliation.jpa.bet.Bet;
import com.gameroundreconciliation.jpa.bet.BetImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Slf4j
public class BetConsumer {

    private final BetImpl betImpl;
    private final AggBetImpl aggBetImpl;
    private final HashMap<Long, AggBet> aggBets = new HashMap<>();

    /**
     * Constructor.
     * @param betImpl - {@link BetImpl}
     * @param aggBetImpl - {@link AggBetImpl}
     * */
    public BetConsumer(BetImpl betImpl, AggBetImpl aggBetImpl){
        this.betImpl = betImpl;
        this.aggBetImpl = aggBetImpl;
    }

    /**
     * MAIN Implementation. This delivers the incoming data into Postgres instantly.
     * PROS:
     * 1) Achieves data deduplication (UPSERT on gameInstanceId).
     * 2) Data ingested in real-time.
     * */
    @KafkaListener(topics = "staging-bets", groupId = "group_id")
    public void consume(Bet bet){
        log.debug("Consumed bet -> {}", bet.toString());
        this.betImpl.upsertBet(bet);
    }

    /**
     * EXPERIMENTAL Implementation. This aggregates data in real time and delivers it into Postgres.
     * PROS:
     * 1) Aggregation done at real-time.
     * 2) Data ingested in real-time.
     * 3) Achieves data deduplication (UPSERT on gameInstanceId).
     * CONS:
     * 1) Doubts regarding overall scalability, due to heavy reliance on In-Memory Hashmap. Investigate usage of KTable Sink / Queryable RocksDB Store.
     * */
    @KafkaListener(topics = "staging-bets", groupId = "group_id_agg")
    public void consumeAgg(Bet bet){
        log.debug("Consumed agg bet -> {}", bet.toString());

        double aggTotalBetAmount;
        double aggTotalWinAmount;

        if (aggBets.containsKey(bet.getGameInstanceId())) {
            aggTotalBetAmount = aggBets.get(bet.getGameInstanceId()).getTotalBetAmount();
            aggTotalWinAmount = aggBets.get(bet.getGameInstanceId()).getTotalWinAmount();
        }else {
            aggTotalBetAmount = 0;
            aggTotalWinAmount = 0;
        }

        AggBet aggBet = new AggBet(
                bet.getGameInstanceId(),
                bet.getUserId(),
                bet.getCreatedTimestamp(),
                nvl(bet.getBonusAmountBet()) + nvl(bet.getRealAmountBet()) + aggTotalBetAmount,
                nvl(bet.getBonusAmountWin()) + nvl(bet.getRealAmountWin()) + aggTotalWinAmount
        );
        aggBets.put(bet.getGameInstanceId(), aggBet);

        this.aggBetImpl.upsertAggBet(aggBets.get(bet.getGameInstanceId()));
    }

    /**
     * Helper method, to treat null values as 0.
     * @param input - {@link Double}
     * @return {@link Double}
     * */
    private Double nvl(Double input){
        if (input == null)
            return .0;
        return input;
    }
}