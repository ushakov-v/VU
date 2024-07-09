package ru.vitaliy.tgBot.controller;

import org.springframework.web.bind.annotation.*;
import ru.vitaliy.tgBot.entity.ClientOrder;
import ru.vitaliy.tgBot.entity.Product;
import ru.vitaliy.tgBot.service.AppService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/rest")
public class ApplicationRestController {
    private final AppService appService;

    public ApplicationRestController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping("/products/search")
    public List<Product> getProductsByCategoryId(@RequestParam Long categoryId) {
        return appService.getProductsByCategoryId(categoryId);
    }

    @GetMapping("/clients/{id}/orders")
    public List<ClientOrder> getClientOrders(@PathVariable Long id) {
        return appService.getClientOrders(id);
    }

    @GetMapping("/clients/{id}/products")
    public Set<Product> getAllProductsInClientId(@PathVariable Long id){
        return appService.getAllProductsInClientId(id);
    }

    @GetMapping("/products/popular")
    public List<Product> getPopularProducts(@RequestParam Integer limit) {
        return appService.getPopularProducts(limit);
    }
}
