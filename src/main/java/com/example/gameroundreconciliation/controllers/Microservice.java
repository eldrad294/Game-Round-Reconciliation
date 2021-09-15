package com.example.gameroundreconciliation.controllers;

import com.example.gameroundreconciliation.jpa.aggbet.AggBetService;
import com.example.gameroundreconciliation.jpa.aggbet.IAggBet;
import com.example.gameroundreconciliation.jpa.bet.BetService;
import com.example.gameroundreconciliation.jpa.bet.Bet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
public class Microservice {

    private final BetService betService;
    private final AggBetService aggBetService;

    public Microservice(BetService betService, AggBetService aggBetService){
        this.betService = betService;
        this.aggBetService = aggBetService;
    }

    @GetMapping("/getBets")
    @ResponseBody
    public ResponseEntity<?> getBets(){
        log.info("Received REST GET:/getBets request");
        List<Bet> betList = this.betService.getBets();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(betList);
    }

    @GetMapping("/getBets/{gameInstanceId}")
    @ResponseBody
    public ResponseEntity<?> getBet(@PathVariable Long gameInstanceId){
        log.info("Received REST GET:/getBets/{} request", gameInstanceId);
        Bet bet = this.betService.getBet(gameInstanceId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bet);
    }

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
}