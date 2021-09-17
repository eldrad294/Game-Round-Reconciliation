package com.gameroundreconciliation.jpa.aggbet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface AggBetRepository extends JpaRepository<AggBet,Long> {

    /**
     * Native Query, performing daily aggregate on demand.
     * @param from - {@link LocalDate}
     * @param to - {@link LocalDate}
     * @param userId - {@link String}
     * @return {@link List} of type {@link IAggBet} projections.
     * */
    @Query( value = "select sb.GAME_ID as gameId, " +
                    "to_char(DATE_TRUNC('day', sb.CREATED_TIMESTAMP),'dd-MM-yyyy') as createdTimestamp, " +
                    "sum(COALESCE(sb.REAL_AMOUNT_BET,0) + COALESCE(sb.BONUS_AMOUNT_BET,0)) as totalBetAmount, " +
                    "sum(COALESCE(sb.REAL_AMOUNT_WIN,0) + COALESCE(sb.BONUS_AMOUNT_WIN,0)) as totalWinAmount " +
                    "from staging_bets sb " +
                    "where sb.CREATED_TIMESTAMP between ?1 and ?2 " +
                    "and sb.USER_ID = ?3 " +
                    "group by sb.GAME_ID, " +
                    "to_char(DATE_TRUNC('day', sb.CREATED_TIMESTAMP),'dd-MM-yyyy')",
            nativeQuery = true)
    List<IAggBet> getDailyAggregates(LocalDate from, LocalDate to, String userId);

    /**
     * Native Query, performing hourly aggregate on demand.
     * @param from - {@link LocalDate}
     * @param to - {@link LocalDate}
     * @param userId - {@link String}
     * @return {@link List} of type {@link IAggBet} projections.
     * */
    @Query( value = "select sb.GAME_ID as gameId, " +
                    "to_char(DATE_TRUNC('hour', sb.CREATED_TIMESTAMP),'dd-MM-yyyy hh') as createdTimestamp, " +
                    "sum(COALESCE(sb.REAL_AMOUNT_BET,0) + COALESCE(sb.BONUS_AMOUNT_BET,0)) as totalBetAmount, " +
                    "sum(COALESCE(sb.REAL_AMOUNT_WIN,0) + COALESCE(sb.BONUS_AMOUNT_WIN,0)) as totalWinAmount " +
                    "from staging_bets sb " +
                    "where sb.CREATED_TIMESTAMP between ?1 and ?2 " +
                    "and sb.USER_ID = ?3 " +
                    "group by sb.GAME_ID, " +
                    "to_char(DATE_TRUNC('hour', sb.CREATED_TIMESTAMP),'dd-MM-yyyy hh')",
            nativeQuery = true)
    List<IAggBet> getHourlyAggregates(LocalDate from, LocalDate to, String userId);

    /**
     * Retrieves pre-computed aggregates
     * @param from - {@link LocalDate}
     * @param to - {@link LocalDate}
     * @param userId - {@link String}
     * @return {@link List} of type {@link AggBet}.
     * */
    List<AggBet> findByCreatedTimestampBetweenAndUserId(Date from, Date to, String userId);

}