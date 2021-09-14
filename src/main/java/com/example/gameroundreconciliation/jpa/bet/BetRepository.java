package com.example.gameroundreconciliation.jpa.bet;

import com.example.gameroundreconciliation.jpa.bet.Bet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BetRepository extends JpaRepository<Bet,Long> {
}
