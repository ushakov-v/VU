package ru.vitaliy.tgBot.service;

import org.springframework.stereotype.Service;
import ru.vitaliy.tgBot.entity.ClientOrder;
import ru.vitaliy.tgBot.entity.Product;
import ru.vitaliy.tgBot.entity.OrderProduct;
import ru.vitaliy.tgBot.repository.ClientOrderRepository;
import ru.vitaliy.tgBot.repository.OrderProductRepository;
import ru.vitaliy.tgBot.repository.ProductRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EntitiesServiceImp {
    private final ProductRepository productRepository;
    private final ClientOrderRepository clientOrderRepository;
    private final OrderProductRepository orderProductRepository;


    public EntitiesServiceImp(ProductRepository productRepository, ClientOrderRepository clientOrderRepository, OrderProductRepository orderProductRepository) {
        this.productRepository = productRepository;
        this.clientOrderRepository = clientOrderRepository;
        this.orderProductRepository = orderProductRepository;
    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<ClientOrder> getClientOrders(Long id) {
        return clientOrderRepository.findByClientId(id);
    }

    public Set<Product> getAllProductsInClientId(Long id) {
        List<ClientOrder> clientOrders = clientOrderRepository.findByClientId(id);
        return clientOrders.stream()
                .flatMap(order -> orderProductRepository.findAllByClientOrderId(order.getId()).stream())
                .map(OrderProduct::getProduct)
                .collect(Collectors.toSet());
    }

    public List<Product> getPopularProducts(Integer limit) {
        List<OrderProduct> allOrderProducts = orderProductRepository.findAll();

        // Сколько раз появляется каждый продукт
        Map<Long, Integer> productCounts = allOrderProducts.stream()
                .collect(Collectors.groupingBy(op -> op.getProduct().getId(), Collectors.summingInt(OrderProduct::getCountProduct)));

        return productCounts.entrySet().stream()
                .sorted(Map.Entry.<Long, Integer>comparingByValue(Comparator.reverseOrder()))
                .limit(limit)
                .map(entry -> productRepository.findById(entry.getKey()).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}