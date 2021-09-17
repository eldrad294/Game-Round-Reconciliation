package com.gameroundreconciliation.controllers;

import com.gameroundreconciliation.jpa.aggbet.AggBet;
import com.gameroundreconciliation.jpa.aggbet.AggBetService;
import com.gameroundreconciliation.jpa.aggbet.IAggBet;
import com.gameroundreconciliation.jpa.bet.BetService;
import com.gameroundreconciliation.jpa.bet.Bet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class Microservice {

    private final BetService betService;
    private final AggBetService aggBetService;

    /**
     * Constructor.
     * @param betService - {@link BetService}
     * @param aggBetService - {@link AggBetService}
     * */
    public Microservice(BetService betService, AggBetService aggBetService){
        this.betService = betService;
        this.aggBetService = aggBetService;
    }

    /**
     * GET REST Endpoint. Retrieves all raw bet values coming from Kafka and landing into Postgres.
     * @return {@link ResponseEntity <?>}
     * */
    @GetMapping("/getBets")
    @ResponseBody
    public ResponseEntity<?> getBets(){
        log.info("Received REST GET:/getBets request");
        List<Bet> betList = this.betService.getBets();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(betList);
    }

    /**
     * GET REST Endpoint. Retrieves all raw bet values coming from Kafka and landing into Postgres, by row unique
     * identifier gameInstanceId.
     * @param gameInstanceId - {@link Long}
     * @return {@link ResponseEntity <?>}
     * */
    @GetMapping("/getBets/{gameInstanceId}")
    @ResponseBody
    public ResponseEntity<?> getBet(@PathVariable Long gameInstanceId){
        log.info("Received REST GET:/getBets/{} request", gameInstanceId);
        Bet bet = this.betService.getBet(gameInstanceId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bet);
    }

    /**
     * GET REST Endpoint. Retrieves daily aggregate breakdown by userId between date range.
     * Daily Aggregates (Performed on Postgres at microservice invocation:
     * 1) Total Bet Amount - Assumption: (REAL_AMOUNT_BET + BONUS_AMOUNT_BET)
     * 2) Total Win Amount - Assumption: (REAL_AMOUNT_WIN + BONUS_AMOUNT_WIN)
     * @param from - {@link String}
     * @param to - {@link String}
     * @param userId - {@link String}
     * @return {@link ResponseEntity <T>}, denoting the daily aggregates.
     * */
    @GetMapping("/getDailyAggregates/{from}/{to}/{userId}")
    @ResponseBody
    public ResponseEntity<?> getDailyAggregates(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable String userId) {
        log.info("Received REST GET:/getDailyAggregates/{}/{}/{} request", from, to, userId);

        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);

        List<IAggBet> result = this.aggBetService.getDailyAggregates(fromDate, toDate, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    /**
     * GET REST Endpoint. Retrieves hourly aggregate breakdown by userId between date range.
     * Hourly Aggregates (Performed on Postgres at microservice invocation:
     * 1) Total Bet Amount - Assumption: (REAL_AMOUNT_BET + BONUS_AMOUNT_BET)
     * 2) Total Win Amount - Assumption: (REAL_AMOUNT_WIN + BONUS_AMOUNT_WIN)
     * @param from - {@link String}
     * @param to - {@link String}
     * @param userId - {@link String}
     * @return {@link ResponseEntity <T>}, denoting the hourly aggregates.
     * */
    @GetMapping("/getHourlyAggregates/{from}/{to}/{userId}")
    @ResponseBody
    public ResponseEntity<?> getHourlyAggregates(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable String userId) {
        log.info("Received REST GET:/getHourlyAggregates/{}/{}/{} request", from, to, userId);

        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);

        List<IAggBet> result = this.aggBetService.getHourlyAggregates(fromDate, toDate, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    /**
     * GET REST Endpoint. Retrieves hourly aggregate breakdown by userId between date range.
     * Game Instance ID Aggregates (Retrieved from Postgres on pre-computed aggregates:
     * 1) Total Bet Amount - Assumption: (REAL_AMOUNT_BET + BONUS_AMOUNT_BET)
     * 2) Total Win Amount - Assumption: (REAL_AMOUNT_WIN + BONUS_AMOUNT_WIN)
     * @param from - {@link String}
     * @param to - {@link String}
     * @param userId - {@link String}
     * @return {@link ResponseEntity <T>}, denoting the Game Instance ID aggregates.
     * */
    @GetMapping("/getPreAggregates/{from}/{to}/{userId}")
    @ResponseBody
    public ResponseEntity<?> getPreAggregates(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable String userId) throws ParseException {
        log.info("Received REST GET:/getPreAggregates/{}/{}/{} request", from, to, userId);

        Date fromDate = new SimpleDateFormat("yyyy-MM-dd").parse(from);
        Date toDate = new SimpleDateFormat("yyyy-MM-dd").parse(to);

        List<AggBet> result = this.aggBetService.getPreAggregates(fromDate, toDate, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }
}