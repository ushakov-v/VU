package ru.vitaliy.tgBot;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class Actions {
    public List<Game> readFile(String filepath) {
        List<Game> records = new ArrayList<>();
        boolean skipHeader = true; // флаг для пропуска первой строки

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue; // пропускаем первую строку с заголовками
                }
                String[] values = line.split(",");
                int user_id = Integer.parseInt(values[0]);
                int hero_id = Integer.parseInt(values[1]);
                int num_games = Integer.parseInt(values[2]);
                records.add(new Game(user_id, hero_id, num_games));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    public List<Map<String, Object>> getPopularHeroes(List<Game> records, int n) {
        Map<Integer, Integer> heroGames = new HashMap<>();
        Map<Integer, Map.Entry<Integer, Integer>> topPlayers = new HashMap<>();

        for (Game record : records) {
            heroGames.put(record.getHeroId(), heroGames.getOrDefault(record.getHeroId(), 0) + record.getNumGames());

            if (!topPlayers.containsKey(record.getHeroId()) || record.getNumGames() > topPlayers.get(record.getHeroId()).getValue()) {
                topPlayers.put(record.getHeroId(), Map.entry(record.getUserId(), record.getNumGames()));
            }
        }

        return heroGames.entrySet().stream().sorted(Map.Entry.<Integer,Integer> comparingByValue().reversed()).limit(n).map(entry -> {
            int heroId = entry.getKey();
            int numAllGames = entry.getValue();
            int userId = topPlayers.get(heroId).getKey();
            Map<String, Object> result = new HashMap<>();
            result.put("heroId", heroId);
            result.put("numAllGames", numAllGames);
            result.put("userId", userId);
            return result;
        }).collect(Collectors.toList());
    }
}
