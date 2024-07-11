package ru.vitaliy.tgBot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ru.vitaliy.tgBot.entity.Client;
import ru.vitaliy.tgBot.entity.ClientOrder;
import ru.vitaliy.tgBot.entity.Product;
import ru.vitaliy.tgBot.repository.CategoryRepository;
import ru.vitaliy.tgBot.repository.ClientOrderRepository;
import ru.vitaliy.tgBot.repository.ClientRepository;
import ru.vitaliy.tgBot.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TelegramBotConnection {

    private TelegramBot bot;
    private final ClientRepository clientRepository;
    private final ClientOrderRepository clientOrderRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private Map<Long, Integer> userStates = new HashMap<>();
    private Map<Long, List<Product>> selectedProducts = new HashMap<>();
    private Map<Long, Client> activeClients = new HashMap<>();  // Добавлено для хранения активных клиентов

    public TelegramBotConnection(ClientRepository clientRepository,
                                 ClientOrderRepository clientOrderRepository,
                                 ProductRepository productRepository,
                                 CategoryRepository categoryRepository) {
        this.clientRepository = clientRepository;
        this.clientOrderRepository = clientOrderRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    private class TelegramUpdatesListener implements UpdatesListener {

        @Override
        public int process(List<Update> updates) {
            updates.forEach(this::processUpdate);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }

        private void processUpdate(Update update) {
            if (update.callbackQuery() != null) {
                String callbackData = update.callbackQuery().data();
                Long chatId = update.callbackQuery().message().chat().id();
                handleCallback(callbackData, chatId);
            } else if (update.message() != null) {
                String userText = update.message().text();
                Long chatId = update.message().chat().id();
                Integer state = userStates.getOrDefault(chatId, 0);
                switch (state) {
                    case 0 -> processClientData(userText, chatId);
                    case 1 -> processMenuSelection(userText, chatId);
                }
            }
        }

        private void handleCallback(String callbackData, long chatId) {
            if (callbackData.startsWith("subcategory,")) {
                String subCategory = callbackData.split(",")[1];
                sendProductsForSubCategory(chatId, subCategory);
            } else if (callbackData.startsWith("product:")) {
                try {
                    int productId = Integer.parseInt(callbackData.replace("product:", ""));
                    Product selectedProduct = productRepository.findById((long) productId).orElse(null);
                    if (selectedProduct != null) {
                        List<Product> products = selectedProducts.getOrDefault(chatId, new ArrayList<>());
                        products.add(selectedProduct);
                        selectedProducts.put(chatId, products);
                        bot.execute(new SendMessage(chatId, "Товар " + selectedProduct.getName() + " добавлен в заказ."));
                    } else {
                        bot.execute(new SendMessage(chatId, "Товар не найден."));
                    }
                } catch (NumberFormatException e) {
                    bot.execute(new SendMessage(chatId, "Некорректный идентификатор товара."));
                }
            }
        }

        private void processMenuSelection(String text, Long chatId) {
            switch (text) {
                case "Пицца", "Классические роллы", "Запеченные роллы", "Сладкие роллы", "Наборы",
                        "Классические бургеры", "Острые бургеры",
                        "Газированные напитки", "Энергетические напитки", "Соки", "Другие" -> {
                    List<Product> products = productRepository.findProductsByCategoryName(text);
                    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                    for (Product product : products) {
                        InlineKeyboardButton button = new InlineKeyboardButton(
                                String.format("Товар %s. Цена %.2f руб.", product.getName(), product.getPrice()))
                                .callbackData(String.format("product:%d", product.getId()));
                        markup.addRow(button);
                    }
                    bot.execute(new SendMessage(chatId, "Товары").replyMarkup(markup));
                }
                case "Роллы" -> sendSubCategoryOptions(chatId, 2L);
                case "Бургеры" -> sendSubCategoryOptions(chatId, 7L);
                case "Напитки" -> sendSubCategoryOptions(chatId, 10L);
                case "Оформить заказ" -> sendOrderSummaryMessage(chatId);
                default -> sendMenuOptions(chatId);
            }
        }

        private void sendStartMessage(Long chatId) {
            sendTextMessage(chatId, "Привет! Я - бот для автоматизации службы доставки. Я помогу тебе быстро сделать заказ.");
            sendTextMessage(chatId, "Введите данные пользователя:\n\n" +
                    "Внимание! Начало сообщения должно начинаться со слова <Данные:>. Пишите в формате <Имя; Номер; Адрес;> \n\n" +
                    "(Пример ввода: Данные: Иван; 87195610937; ул. Макаренко, 32");
        }

        private void processClientData(String text, Long chatId) {
            if (text != null) {
                if (text.startsWith("/start")) {
                    sendStartMessage(chatId);
                } else if (text.startsWith("Данные: ")) {
                    String[] userData = text.substring("Данные: ".length()).split(";");
                    if (userData.length >= 3) {
                        Client client = new Client();
                        client.setExternalId(chatId);
                        client.setFullName(userData[0]);
                        client.setPhoneNumber(userData[1]);
                        client.setAddress(userData[2]);
                        clientRepository.save(client);

                        activeClients.put(chatId, client);

                        sendTextMessage(chatId, "Данные успешно записаны");
                        sendMenuOptions(chatId);
                        userStates.put(chatId, 1);
                    } else {
                        sendTextMessage(chatId, "Неверный формат ввода. Пожалуйста, введите данные в следующем формате: Имя; Адрес; Номер.");
                        sendTextMessage(chatId, "Введите данные пользователя заново:");
                    }
                } else {
                    sendTextMessage(chatId, "Введите команду /start для начала.");
                }
            }
        }

        private void sendMenuOptions(Long chatId) {
            ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup(
                    new KeyboardButton("Пицца"),
                    new KeyboardButton("Роллы"),
                    new KeyboardButton("Бургеры"),
                    new KeyboardButton("Напитки")
            ).addRow(
                    new KeyboardButton("Оформить заказ")
            ).resizeKeyboard(true);
            bot.execute(new SendMessage(chatId, "Выберите пункт меню:").replyMarkup(keyboard));
        }



        private void sendSubCategoryOptions(Long chatId, Long parentId) {
            List<KeyboardButton> categories = categoryRepository.findCategoriesByParentId(parentId)
                    .stream()
                    .map(category -> new KeyboardButton(category.getName()))
                    .collect(Collectors.toList());
            ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(categories.toArray(new KeyboardButton[0]));
            markup.resizeKeyboard(true);
            markup.addRow(new KeyboardButton("Оформить заказ"));
            markup.addRow(new KeyboardButton("В основное меню"));
            bot.execute(new SendMessage(chatId, "Выберите подкатегорию:").replyMarkup(markup));
        }

        private void sendProductsForSubCategory(Long chatId, String subCategory) {
            List<Product> products = productRepository.findProductsByCategoryName(subCategory);
            if (products != null && !products.isEmpty()) {
                InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                for (Product product : products) {
                    InlineKeyboardButton button = new InlineKeyboardButton(
                            String.format("Товар %s. Цена %.2f руб.", product.getName(), product.getPrice()))
                            .callbackData(String.format("product:%d", product.getId()));
                    markup.addRow(button);
                }
                bot.execute(new SendMessage(chatId, "Товары:").replyMarkup(markup));
            } else {
                sendTextMessage(chatId, "В этой категории нет товаров.");
            }
        }

        private void sendOrderSummaryMessage(Long chatId) {
            List<Product> products = selectedProducts.get(chatId);
            if (products != null && !products.isEmpty()) {
                StringBuilder orderSummary = new StringBuilder("Поздравляем с Заказом!:\n");
                BigDecimal totalCost = products.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

                Client client = activeClients.get(chatId);

                if (client != null) {
                    ClientOrder clientOrder = new ClientOrder();
                    clientOrder.setClient(client);
                    clientOrder.setStatus(1);
                    clientOrder.setTotal(totalCost);
                    clientOrderRepository.save(clientOrder);

                    orderSummary.append("\nОбщая стоимость: ").append(totalCost).append(" руб.\n");
                    orderSummary.append("\nВаш заказ оформлен. Курьер приедет по адресу: ").append(client.getAddress()).append(".");

                    sendTextMessage(chatId, orderSummary.toString());
                    selectedProducts.remove(chatId);
                } else {
                    sendTextMessage(chatId, "Клиент с таким chatId не найден.");
                }
            } else {
                sendTextMessage(chatId, "Ваш заказ пуст. Выберите продукты из меню.");
            }
        }

        private void sendTextMessage(Long chatId, String text) {
            bot.execute(new SendMessage(chatId, text));
        }
    }

    @PostConstruct
    public void createConnection() {
        String botToken = "7498111931:AAGhABDvXv-BSQJBWgP2SNCFb9JwVAVxZQw";
        bot = new TelegramBot(botToken);
        bot.setUpdatesListener(new TelegramUpdatesListener());
    }
}
