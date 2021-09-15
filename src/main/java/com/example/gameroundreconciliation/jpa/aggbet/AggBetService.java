package com.example.gameroundreconciliation.jpa.aggbet;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface AggBetService {

    List<IAggBet> getDailyAggregates(LocalDate from, LocalDate to, String userId);

    List<IAggBet> getHourlyAggregates(LocalDate from, LocalDate to, String userId);

}