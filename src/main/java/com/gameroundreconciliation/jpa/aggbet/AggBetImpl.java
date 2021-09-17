package com.gameroundreconciliation.jpa.aggbet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AggBetImpl implements AggBetService {

    private final AggBetRepository aggBetRepository;

    /**
     * Constructor.
     * @param aggBetRepository - {@link AggBetRepository}
     * */
    public AggBetImpl(AggBetRepository aggBetRepository){
        this.aggBetRepository = aggBetRepository;
    }

    /**
     * Daily aggregate service.
     * @param from - {@link LocalDate}
     * @param to - {@link LocalDate}
     * @param userId - {@link String}
     * @return {@link List} of retrieved {@link IAggBet} projections.
     * */
    @Override
    public List<IAggBet> getDailyAggregates(LocalDate from, LocalDate to, String userId) {
        return this.aggBetRepository.getDailyAggregates(from, to, userId);
    }

    /**
     * Hourly aggregate service.
     * @param from - {@link LocalDate}
     * @param to - {@link LocalDate}
     * @param userId - {@link String}
     * @return {@link List} of retrieved {@link IAggBet} projections.
     * */
    @Override
    public List<IAggBet> getHourlyAggregates(LocalDate from, LocalDate to, String userId) {
        return this.aggBetRepository.getHourlyAggregates(from, to, userId);
    }

    /**
     * Upsert service, which achieves database upsertion and deduplication of bets based off the GAME_INSTANCE_ID
     * dimension.
     * @param aggBet - {@link AggBet}
     * */
    @Override
    public void upsertAggBet(AggBet aggBet){
        if (aggBet.getGameInstanceId() == null)
            throw new IllegalArgumentException("Received agg bet with an empty GameInstanceId!");

        Optional<AggBet> result = this.aggBetRepository.findById(aggBet.getGameInstanceId());
        if (result.isEmpty()) {
            this.aggBetRepository.save(aggBet);
            log.info("Inserted agg bet -> {}", aggBet);
        } else {

            AggBet updateAggBet = result.get();

            updateAggBet.setUserId(aggBet.getUserId());
            updateAggBet.setTotalBetAmount(aggBet.getTotalBetAmount());
            updateAggBet.setCreatedTimestamp(aggBet.getCreatedTimestamp());
            updateAggBet.setTotalWinAmount(aggBet.getTotalWinAmount());

            this.aggBetRepository.save(updateAggBet);
            log.info("Updated agg bet -> {}", updateAggBet);

        }
    }

    /**
     * Pre aggregate service.
     * @param from - {@link LocalDate}
     * @param to - {@link LocalDate}
     * @param userId - {@link String}
     * @return {@link List} of retrieved {@link AggBet} aggregates.
     * */
    @Override
    public List<AggBet> getPreAggregates(Date from, Date to, String userId) {
        return this.aggBetRepository.findByCreatedTimestampBetweenAndUserId(from, to, userId);
    }
}