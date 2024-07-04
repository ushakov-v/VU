package ru.vitaliy.tgBot;

public class Game {
    private int user_id;
    private int hero_id;
    private int num_games;

    public Game(int user_id, int hero_id, int num_games) {
        this.user_id = user_id;
        this.hero_id = hero_id;
        this.num_games = num_games;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public int getHeroId() {
        return hero_id;
    }

    public void setHeroId(int hero_id) {
        this.hero_id = hero_id;
    }

    public int getNumGames() {
        return num_games;
    }

    public void setNumGames(int num_games) {
        this.num_games = num_games;
    }
}
