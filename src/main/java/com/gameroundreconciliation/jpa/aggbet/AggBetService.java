package com.gameroundreconciliation.jpa.aggbet;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public interface AggBetService {

    /**
     * Daily aggregate service.
     * @param from - {@link LocalDate}
     * @param to - {@link LocalDate}
     * @param userId - {@link String}
     * @return {@link List} of retrieved {@link IAggBet} projections.
     * */
    List<IAggBet> getDailyAggregates(LocalDate from, LocalDate to, String userId);

    /**
     * Hourly aggregate service.
     * @param from - {@link LocalDate}
     * @param to - {@link LocalDate}
     * @param userId - {@link String}
     * @return {@link List} of retrieved {@link IAggBet} projections.
     * */
    List<IAggBet> getHourlyAggregates(LocalDate from, LocalDate to, String userId);

    /**
     * Upsert service, which achieves database upsertion and deduplication of bets based off the GAME_INSTANCE_ID
     * dimension.
     * @param aggBet - {@link AggBet}
     * */
    void upsertAggBet(AggBet aggBet);

    /**
     * Pre aggregate service.
     * @param from - {@link LocalDate}
     * @param to - {@link LocalDate}
     * @param userId - {@link String}
     * @return {@link List} of retrieved {@link AggBet} aggregates.
     * */
    List<AggBet> getPreAggregates(Date from, Date to, String userId);

}