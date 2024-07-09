package ru.vitaliy.tgBot.service;

import ru.vitaliy.tgBot.entity.Client;
import ru.vitaliy.tgBot.entity.ClientOrder;
import ru.vitaliy.tgBot.entity.Product;

import java.util.List;
import java.util.Set;

/**
 * Сервис для работы с сущностями телеграмм-бота
 */

public interface EntitiesService {
    /**
     * Получить список товаров в категории
     * @param id идентификатор категории
     */
    List<Product> getProductsByCategoryId(Long id);
    /**
     * Получить список заказов клиента
     * @param id идентификатор клиента
     */
    List<ClientOrder> getClientOrders(Long id);
    /**
     * Получить список всех товаров во всех заказах клиента
     * @param id идентификатор клиента
     */
    Set<Product> getClientProducts(Long id);
    /**
     * Получить указанное кол-во самых популярных (наибольшее
     * количество штук в заказах) товаров среди клиентов
     * @param limit максимальное кол-во товаров
     */
    List<Product> getTopPopularProducts(Integer limit);
    /**
     * Найти всех клиентов по подстроке имени
     * @param name подстрока имени клиента
     */
    default List<Client> searchClientsByName(String name) {
        throw new UnsupportedOperationException("Доп. задание");
    }
    /**
     * Найти все продукты по подстроке названия
     * @param name подстрока названия продукта
     */
    default List<Product> searchProductsByName(String name) {
        throw new UnsupportedOperationException("Доп. задание");
    }

}
