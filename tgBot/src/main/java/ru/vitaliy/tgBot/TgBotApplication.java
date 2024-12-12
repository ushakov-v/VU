package ru.vitaliy.tgBot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class TgBotApplication implements CommandLineRunner {

	@Autowired
	private Actions actions;

	public static void main(String[] args) {
		SpringApplication.run(TgBotApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (args.length != 2) {
			System.err.println("Usage: java -jar tgBot.jar <csv-file-path> <n>");
			return;
		}

		String filePath = args[0];
		int n = Integer.parseInt(args[1]);

		List<Game> records = actions.readFile(filePath);
		List<Map<String, Object>> popularHeroes = actions.getPopularHeroes(records, n);

		System.out.println("| hero_id | num_all_games | user_id |");
		for (Map<String, Object> hero : popularHeroes) {
			System.out.printf("| %7d | %13d | %7d |%n", hero.get("heroId"), hero.get("numAllGames"), hero.get("userId"));
		}
	}
}
