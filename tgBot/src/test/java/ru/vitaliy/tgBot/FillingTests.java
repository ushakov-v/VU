package ru.vitaliy.tgBot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vitaliy.tgBot.entity.Category;
import ru.vitaliy.tgBot.entity.Client;
import ru.vitaliy.tgBot.entity.Product;
import ru.vitaliy.tgBot.repository.CategoryRepository;
import ru.vitaliy.tgBot.repository.ClientRepository;
import ru.vitaliy.tgBot.repository.ProductRepository;

import java.math.BigDecimal;

@SpringBootTest
public class FillingTests {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    @Test
    void createTwoClients(){
        Client client1 = new Client();
        client1.setExternalId(1L);
        client1.setFullName("Бондарев Максим Владиславович");
        client1.setPhoneNumber("89147263908");
        client1.setAddress("Севастополь");
        clientRepository.save(client1);

        Client client2 = new Client();
        client2.setExternalId(2L);
        client2.setFullName("Высоцкая Юлия Романовна");
        client2.setPhoneNumber("89219306129");
        client2.setAddress("Москва");
        clientRepository.save(client2);
    }

    @Test
    void createCategoriesAndProducts() {
        // Пицца
        Category pizza = new Category();
        pizza.setName("Пицца");
        pizza.setParent(null);
        categoryRepository.save(pizza);

        // Роллы
        Category rolls = new Category();
        rolls.setName("Роллы");
        rolls.setParent(null);
        categoryRepository.save(rolls);

        Category classicRolls = new Category();
        classicRolls.setName("Классические роллы");
        classicRolls.setParent(rolls.getParent());
        categoryRepository.save(classicRolls);

        Category bakedRolls = new Category();
        bakedRolls.setName("Запеченные роллы");
        bakedRolls.setParent(rolls.getParent());
        categoryRepository.save(bakedRolls);

        Category sweetRolls = new Category();
        sweetRolls.setName("Сладкие роллы");
        sweetRolls.setParent(rolls.getParent());
        categoryRepository.save(sweetRolls);

        Category sets = new Category();
        sets.setName("Наборы");
        sets.setParent(rolls.getParent());
        categoryRepository.save(sets);

        // Бургеры
        Category burgers = new Category();
        burgers.setName("Бургеры");
        burgers.setParent(null);
        categoryRepository.save(burgers);

        Category classicBurgers = new Category();
        classicBurgers.setName("Классические бургеры");
        classicBurgers.setParent(burgers.getParent());
        categoryRepository.save(classicBurgers);

        Category spicyBurgers = new Category();
        spicyBurgers.setName("Острые бургеры");
        spicyBurgers.setParent(burgers.getParent());
        categoryRepository.save(spicyBurgers);

        // Напитки
        Category drinks = new Category();
        drinks.setName("Напитки");
        drinks.setParent(null);
        categoryRepository.save(drinks);

        Category carbonatedDrinks = new Category();
        carbonatedDrinks.setName("Газированные напитки");
        carbonatedDrinks.setParent(drinks.getParent());
        categoryRepository.save(carbonatedDrinks);

        Category energyDrinks = new Category();
        energyDrinks.setName("Энергетические напитки");
        energyDrinks.setParent(drinks.getParent());
        categoryRepository.save(energyDrinks);

        Category juices = new Category();
        juices.setName("Соки");
        juices.setParent(drinks.getParent());
        categoryRepository.save(juices);

        Category otherDrinks = new Category();
        otherDrinks.setName("Другие");
        otherDrinks.setParent(drinks.getParent());
        categoryRepository.save(otherDrinks);

        // Продукты для пиццы
        Product margherita = new Product();
        margherita.setCategory(pizza);
        margherita.setName("Маргарита");
        margherita.setDescription("Классическая итальянская пицца с томатным соусом, моцареллой и базиликом.");
        margherita.setPrice(BigDecimal.valueOf(425.0));
        productRepository.save(margherita);

        Product hawaiian = new Product();
        hawaiian.setCategory(pizza);
        hawaiian.setName("Гавайская");
        hawaiian.setDescription("Популярная вариация пиццы с ананасами, ветчиной и сыром.");
        hawaiian.setPrice(BigDecimal.valueOf(450.0));
        productRepository.save(hawaiian);

        Product fourSeasons = new Product();
        fourSeasons.setCategory(pizza);
        fourSeasons.setName("Четыре сезона");
        fourSeasons.setDescription("Пицца, разделенная на четыре части, каждая из которых содержит разные ингредиенты, представляющие различные сезоны года.");
        fourSeasons.setPrice(BigDecimal.valueOf(470.0));
        productRepository.save(fourSeasons);

        // Продукты для классических роллов
        Product philadelphiaRoll = new Product();
        philadelphiaRoll.setCategory(classicRolls);
        philadelphiaRoll.setName("Филадельфия");
        philadelphiaRoll.setDescription("Ролл с лососем, сливочным сыром и свежими огурцами.");
        philadelphiaRoll.setPrice(BigDecimal.valueOf(500.0));
        productRepository.save(philadelphiaRoll);

        Product californiaRoll = new Product();
        californiaRoll.setCategory(classicRolls);
        californiaRoll.setName("Калифорния");
        californiaRoll.setDescription("Ролл с крабовым мясом, авокадо и масаго (икра тобико).");
        californiaRoll.setPrice(BigDecimal.valueOf(425.0));
        productRepository.save(californiaRoll);

        Product spicyTunaRoll = new Product();
        spicyTunaRoll.setCategory(classicRolls);
        spicyTunaRoll.setName("Спайси Тунец");
        spicyTunaRoll.setDescription("Ролл с тунцом, чили соусом и свежим огурцом.");
        spicyTunaRoll.setPrice(BigDecimal.valueOf(280.0));
        productRepository.save(spicyTunaRoll);

        // Продукты для запеченных роллов
        Product tempuraRoll = new Product();
        tempuraRoll.setCategory(bakedRolls);
        tempuraRoll.setName("Темпура Ролл");
        tempuraRoll.setDescription("Ролл с темпурой (жареным во фритюре морепродуктом), обычно креветками, и свежими овощами.");
        tempuraRoll.setPrice(BigDecimal.valueOf(320.0));
        productRepository.save(tempuraRoll);

        Product salmonNigiri = new Product();
        salmonNigiri.setCategory(bakedRolls);
        salmonNigiri.setName("Салмон Нигири");
        salmonNigiri.setDescription("Нарезанный лосось на рисе.");
        salmonNigiri.setPrice(BigDecimal.valueOf(350.0));
        productRepository.save(salmonNigiri);

        Product unagiMaki = new Product();
        unagiMaki.setCategory(bakedRolls);
        unagiMaki.setName("Унаги Маки");
        unagiMaki.setDescription("Ролл с копченой угрем и рисом.");
        unagiMaki.setPrice(BigDecimal.valueOf(300.0));
        productRepository.save(unagiMaki);

        // Продукты для сладких роллов
        Product bananaRoll = new Product();
        bananaRoll.setCategory(sweetRolls);
        bananaRoll.setName("Банановый Ролл");
        bananaRoll.setDescription("Ролл с бананом, обваленный в кокосовой стружке.");
        bananaRoll.setPrice(BigDecimal.valueOf(200.0));
        productRepository.save(bananaRoll);

        Product chocolateRoll = new Product();
        chocolateRoll.setCategory(sweetRolls);
        chocolateRoll.setName("Шоколадный Ролл");
        chocolateRoll.setDescription("Ролл с шоколадной начинкой и фруктами.");
        chocolateRoll.setPrice(BigDecimal.valueOf(220.0));
        productRepository.save(chocolateRoll);

        Product berryRoll = new Product();
        berryRoll.setCategory(sweetRolls);
        berryRoll.setName("Ягодный Ролл");
        berryRoll.setDescription("Ролл с ягодной начинкой и взбитыми сливками.");
        berryRoll.setPrice(BigDecimal.valueOf(230.0));
        productRepository.save(berryRoll);

        // Продукты для наборов
        Product basicSet = new Product();
        basicSet.setCategory(sets);
        basicSet.setName("Базовый Сет");
        basicSet.setDescription("Сет из 5 классических роллов: 2 салмона, 2 тунца и 1 темпуры.");
        basicSet.setPrice(BigDecimal.valueOf(1000.0));
        productRepository.save(basicSet);

        Product luxurySet = new Product();
        luxurySet.setCategory(sets);
        luxurySet.setName("Люкс Сет");
        luxurySet.setDescription("Элитный сет из 6 роллов: 2 унаги, 2 крабовых и 2 японских овощей.");
        luxurySet.setPrice(BigDecimal.valueOf(1200.0));
        productRepository.save(luxurySet);

        Product rushSet = new Product();
        rushSet.setCategory(sets);
        rushSet.setName("Форсаж Сет");
        rushSet.setDescription("Активный сет из 8 роллов: 4 темпуры, 2 креветки и 2 осьминога.");
        rushSet.setPrice(BigDecimal.valueOf(1500.0));
        productRepository.save(rushSet);

        // Продукты для классических бургеров
        Product basicBurger = new Product();
        basicBurger.setCategory(classicBurgers);
        basicBurger.setName("Бургер Базовый");
        basicBurger.setDescription("Классический бургер с котлетой из говядины, сыром чеддер, луком и кетчупом.");
        basicBurger.setPrice(BigDecimal.valueOf(250.0));
        productRepository.save(basicBurger);

        Product cheeseburger = new Product();
        cheeseburger.setCategory(classicBurgers);
        cheeseburger.setName("Чизбургер");
        cheeseburger.setDescription("Бургер с котлетой из говядины, сыром чеддер внутри и на верхней булочке.");
        cheeseburger.setPrice(BigDecimal.valueOf(300.0));
        productRepository.save(cheeseburger);

        Product giantBurger = new Product();
        giantBurger.setCategory(classicBurgers);
        giantBurger.setName("Бургер Гигант");
        giantBurger.setDescription("Огромный бургер с двумя котлетами из говядины, сыром эмменталь, авокадо и специальным соусом.");
        giantBurger.setPrice(BigDecimal.valueOf(450.0));
        productRepository.save(giantBurger);

        // Продукты для острых бургеров
        Product hotPepperoniBurger = new Product();
        hotPepperoniBurger.setCategory(spicyBurgers);
        hotPepperoniBurger.setName("Хот Пепперони Бургер");
        hotPepperoniBurger.setDescription("Бургер с котлетой из говядины, сыром чеддер, пепперони и горячим соусом.");
        hotPepperoniBurger.setPrice(BigDecimal.valueOf(350.0));
        productRepository.save(hotPepperoniBurger);

        Product mexicanHotBurger = new Product();
        mexicanHotBurger.setCategory(spicyBurgers);
        mexicanHotBurger.setName("Мексиканский Хот Бургер");
        mexicanHotBurger.setDescription("Бургер с котлетой из говядины, сыром чеддер, авокадо, красным перцем и мексиканским соусом.");
        mexicanHotBurger.setPrice(BigDecimal.valueOf(380.0));
        productRepository.save(mexicanHotBurger);

        Product signatureSauceBurger = new Product();
        signatureSauceBurger.setCategory(spicyBurgers);
        signatureSauceBurger.setName("Бургер С Фирменным Соусом");
        signatureSauceBurger.setDescription("Бургер с котлетой из говядины, сыром чеддер, свежими овощами и фирменным острым соусом.");
        signatureSauceBurger.setPrice(BigDecimal.valueOf(390.0));
        productRepository.save(signatureSauceBurger);

        // Продукты для газированных напитков
        Product cola = new Product();
        cola.setCategory(carbonatedDrinks);
        cola.setName("Кола");
        cola.setDescription("Традиционная кола с тонким вкусом и пикантным послевкусием.");
        cola.setPrice(BigDecimal.valueOf(80.0));
        productRepository.save(cola);

        Product sprite = new Product();
        sprite.setCategory(carbonatedDrinks);
        sprite.setName("Спрайт");
        sprite.setDescription("Легкий и освежающий газированный напиток с лимонным вкусом.");
        sprite.setPrice(BigDecimal.valueOf(90.0));
        productRepository.save(sprite);

        Product fanta = new Product();
        fanta.setCategory(carbonatedDrinks);
        fanta.setName("Фанта");
        fanta.setDescription("Газированный напиток с ярким апельсиновым вкусом.");
        fanta.setPrice(BigDecimal.valueOf(85.0));
        productRepository.save(fanta);

        // Продукты для энергетических напитков
        Product redBull = new Product();
        redBull.setCategory(energyDrinks);
        redBull.setName("Ред Булл");
        redBull.setDescription("Энергетический напиток с высоким содержанием кофеина и таурина для повышения концентрации и энергии.");
        redBull.setPrice(BigDecimal.valueOf(180.0));
        productRepository.save(redBull);

        Product monsterEnergy = new Product();
        monsterEnergy.setCategory(energyDrinks);
        monsterEnergy.setName("Monster Energy");
        monsterEnergy.setDescription("Энергетический напиток с уникальным сочетанием вкусов и добавками для увеличения физической активности.");
        monsterEnergy.setPrice(BigDecimal.valueOf(190.0));
        productRepository.save(monsterEnergy);

        Product rockstar = new Product();
        rockstar.setCategory(energyDrinks);
        rockstar.setName("Rockstar");
        rockstar.setDescription("Энергетический напиток с комплексом витаминов и минералов для поддержания энергии на протяжении дня.");
        rockstar.setPrice(BigDecimal.valueOf(170.0));
        productRepository.save(rockstar);

        // Продукты для соков
        Product orangeJuice = new Product();
        orangeJuice.setCategory(juices);
        orangeJuice.setName("Апельсиновый Сок");
        orangeJuice.setDescription("Концентрированный апельсиновый сок с натуральным ароматом цитрусов.");
        orangeJuice.setPrice(BigDecimal.valueOf(120.0));
        productRepository.save(orangeJuice);

        Product appleJuice = new Product();
        appleJuice.setCategory(juices);
        appleJuice.setName("Яблочный Сок");
        appleJuice.setDescription("Янтарно-золотистый яблочный сок с нотками зеленых яблок.");
        appleJuice.setPrice(BigDecimal.valueOf(110.0));
        productRepository.save(appleJuice);

        Product pomegranateJuice = new Product();
        pomegranateJuice.setCategory(juices);
        pomegranateJuice.setName("Гранатовый Сок");
        pomegranateJuice.setDescription("Невероятно вкусный гранатовый сок с богатым содержанием витамина C.");
        pomegranateJuice.setPrice(BigDecimal.valueOf(130.0));
        productRepository.save(pomegranateJuice);

        // Продукты для других напитков
        Product greenTea = new Product();
        greenTea.setCategory(otherDrinks);
        greenTea.setName("Зеленый Чай");
        greenTea.setDescription("Натуральный зеленый чай с легким ароматом и свежестью.");
        greenTea.setPrice(BigDecimal.valueOf(150.0));
        productRepository.save(greenTea);

        Product cherryCompote = new Product();
        cherryCompote.setCategory(otherDrinks);
        cherryCompote.setName("Компот Из Черники");
        cherryCompote.setDescription("Традиционный компот из сочных черных ягод с медовым ароматом.");
        cherryCompote.setPrice(BigDecimal.valueOf(160.0));
        productRepository.save(cherryCompote);

        Product instantCoffee = new Product();
        instantCoffee.setCategory(otherDrinks);
        instantCoffee.setName("Инстант Кофе");
        instantCoffee.setDescription("Универсальный растворимый кофе для быстрого заваривания.");
        instantCoffee.setPrice(BigDecimal.valueOf(180.0));
        productRepository.save(instantCoffee);
    }
}
