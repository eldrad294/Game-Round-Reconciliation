package com.example.gameroundreconciliation.controllers;

import com.example.gameroundreconciliation.jpa.bet.BetService;
import com.example.gameroundreconciliation.jpa.bet.Bet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class Microservice {

    private final BetService betService;

    public Microservice(BetService betService){
        this.betService = betService;
    }

    @GetMapping("microservice/getBets")
    @ResponseBody
    public ResponseEntity<?> getBets(){
        List<Bet> betList = this.betService.getBets();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(betList);
    }

    @GetMapping("microservice/getBets/{gameInstanceId}")
    @ResponseBody
    public ResponseEntity<?> getBet(@PathVariable Long gameInstanceId){
        Bet bet = this.betService.getBet(gameInstanceId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bet);
    }
}
