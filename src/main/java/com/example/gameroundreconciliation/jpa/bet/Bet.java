package com.example.gameroundreconciliation.jpa.bet;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "staging_bets")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Bet {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("created_timestamp")
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "CREATED_TIMESTAMP")
    private Date createdTimestamp;

    @JsonProperty("game_instance_id")
    @Column(name = "GAME_INSTANCE_ID")
    private Long gameInstanceId;

    @JsonProperty("user_id")
    @Column(name = "USER_ID")
    private String userId;

    @JsonProperty("game_id")
    @Column(name = "GAME_ID")
    private Long gameId;

    @JsonProperty("real_amount_bet")
    @Column(name = "REAL_AMOUNT_BET")
    private Double realAmountBet;

    @JsonProperty("bonus_amount_bet")
    @Column(name = "BONUS_AMOUNT_BET")
    private Double bonusAmountBet;

    @JsonProperty("real_amount_win")
    @Column(name = "REAL_AMOUNT_WIN")
    private Double realAmountWin;

    @JsonProperty("bonus_amount_win")
    @Column(name = "BONUS_AMOUNT_WIN")
    private Double bonusAmountWin;

    @JsonProperty("game_name")
    @Column(name = "GAME_NAME")
    private String gameName;

    @JsonProperty("provider")
    @Column(name = "PROVIDER")
    private String provider;
}
