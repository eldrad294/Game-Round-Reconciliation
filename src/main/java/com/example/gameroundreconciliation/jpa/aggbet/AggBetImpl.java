package com.example.gameroundreconciliation.jpa.aggbet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class AggBetImpl implements AggBetService {

    private final AggBetRepository aggBetRepository;

    public AggBetImpl(AggBetRepository aggBetRepository){
        this.aggBetRepository = aggBetRepository;
    }


    @Override
    public List<IAggBet> getDailyAggregates(LocalDate from, LocalDate to, String userId) {
        return this.aggBetRepository.getDailyAggregates(from, to, userId);
    }

    @Override
    public List<IAggBet> getHourlyAggregates(LocalDate from, LocalDate to, String userId) {
        return this.aggBetRepository.getHourlyAggregates(from, to, userId);
    }
}