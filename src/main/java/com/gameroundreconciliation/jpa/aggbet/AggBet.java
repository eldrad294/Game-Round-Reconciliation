package com.gameroundreconciliation.jpa.aggbet;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "staging_bets_agg")
public class AggBet {

    /**
     * Constructor for aggregate bet structure.
     * Used in experimental approach where aggregates are computed at real-time.
     * @param gameInstanceId - {@link Long}
     * @param userId - {@link String}
     * @param createdTimestamp - {@link Date}
     * @param totalBetAmount - {@link Double}
     * @param totalWinAmount - {@link Double}
     * */
    public AggBet(Long gameInstanceId, String userId, Date createdTimestamp, Double totalBetAmount, Double totalWinAmount){
        this.gameInstanceId = gameInstanceId;
        this.userId = userId;
        this.createdTimestamp = createdTimestamp;
        this.totalBetAmount = totalBetAmount;
        this.totalWinAmount = totalWinAmount;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="GAME_INSTANCE_ID")
    private Long gameInstanceId;

    @Column(name="USER_ID")
    private String userId;

    @Column(name="CREATED_TIMESTAMP")
    private Date createdTimestamp;

    @Column(name="TOTAL_BET_AMOUNT")
    private Double totalBetAmount;

    @Column(name="TOTAL_WIN_AMOUNT")
    private Double totalWinAmount;
}
