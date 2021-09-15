package com.example.gameroundreconciliation.jpa.aggbet;

public interface IAggBet {

    Long getGameId();
    String getCreatedTimestamp();
    Double getTotalBetAmount();
    Double getTotalWinAmount();

}