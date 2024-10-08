package com.main.game;

import com.main.general.Printer;
import com.main.game.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameController {

    public Playfield playfield;
    private final Printer printer;
    public List<Player> players = new ArrayList<Player>();
    private int round = 0;
    private int roundLimit = 0;
    private int activePlayerIndex = 0;

    public int getRound(){
        return round;
    }

    public void setRound(int round){
        this.round = round;
    }

    public int getRoundLimit(){
        return roundLimit;
    }

    public void setRoundLimit(int roundLimit){
        this.roundLimit = roundLimit;
    }


    public int getActivePlayerIndex() {
        return activePlayerIndex;
    }

    public void setActivePlayerIndex(int activePlayerIndex) {
        this.activePlayerIndex = activePlayerIndex;
    }


    public GameController(Map<String, Integer> settings) {

        int rows = settings.get("rows");
        int columns = settings.get("cols");
        this.setRoundLimit(settings.get("roundLimit"));
        this.playfield = new Playfield(rows, columns);
        this.printer = new Printer();
    }

    public void initializeNewGame(){
        playfield.InitializeNewPlayField();
    }


    public void addNewPlayer(String playerName) {

        int iconIndex = players.size()+1; // 1 = Spieler1 (Ω), 2 = Spieler 2 (§)
        Player player = new Player(playerName, iconIndex, this.playfield);
        players.add(player);
    }

    // Starte ein neues Spiel und setze alle Werte auf Start
    public void startNewGame() {
        setRound(0);
        setActivePlayerIndex(-1);
        nextRound();
    }

    // Springe zum nächsten Spieler
    public void NextPlayer(){
        setActivePlayerIndex(getActivePlayerIndex() + 1);
    }

    // Springe zur nächsten Runde
    public void nextRound(){
        setActivePlayerIndex(0);
        setRound(getRound()+1);
    }

    // Erhalte das Spielfeld in dem Anzeigeformat
    public void getPlayfield() {
        String[] playerIcons = new String[]{"✈\uFE0F", "\uD83D\uDEE9\uFE0F", "\uD83D\uDE81"};
        Player player = players.get(getActivePlayerIndex()); // Aktueller Spieler
        printer.clear();
        printer.print_bold("Runde: " + getRound() + " von " + getRoundLimit() + " | ");
        printer.println_bold( player.getName() + " ist am Zug! (" + playerIcons[getActivePlayerIndex()] + ") | " + player.getHealth() + "HP", "orange");
        playfield.showPlayField();
    }

}
