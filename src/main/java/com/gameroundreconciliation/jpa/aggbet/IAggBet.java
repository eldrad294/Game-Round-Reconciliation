package com.gameroundreconciliation.jpa.aggbet;

/**
 * Projection interface, for nativr query mapping.
 * */
public interface IAggBet {

    Long getGameId();
    String getCreatedTimestamp();
    Double getTotalBetAmount();
    Double getTotalWinAmount();

}