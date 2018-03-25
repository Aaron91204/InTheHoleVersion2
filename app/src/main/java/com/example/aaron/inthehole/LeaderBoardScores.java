package com.example.aaron.inthehole;

/**
 * Created by Aaron on 25/03/2018.
 */

public class LeaderBoardScores {
    private String Gross;
    private String PlayerHandicap;
    private String Net;
    private String FullName;
    public LeaderBoardScores ()
    {

    }

    public String getGross() {
        return Gross;
    }

    public void setGross(String gross) {
        Gross = gross;
    }

    public String getPlayerHandicap() {
        return PlayerHandicap;
    }

    public void setPlayerHandicap(String playerHandicap) {
        PlayerHandicap = playerHandicap;
    }

    public String getNet() {
        return Net;
    }

    public void setNet(String net) {
        Net = net;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }
}
