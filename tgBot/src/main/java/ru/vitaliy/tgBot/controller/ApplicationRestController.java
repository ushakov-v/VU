package ru.vitaliy.tgBot.controller;

import org.springframework.web.bind.annotation.*;
import ru.vitaliy.tgBot.entity.Category;
import ru.vitaliy.tgBot.entity.ClientOrder;
import ru.vitaliy.tgBot.entity.Product;
import ru.vitaliy.tgBot.service.EntitiesServiceImp;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/rest")
public class ApplicationRestController {
    private final EntitiesServiceImp entitiesServiceImp;

    public ApplicationRestController(EntitiesServiceImp entitiesServiceImp) {
        this.entitiesServiceImp = entitiesServiceImp;
    }

    @GetMapping("/products/search")
    public List<Product> getProductsByCategoryId(@RequestParam Long categoryId) {
        return entitiesServiceImp.getProductsByCategoryId(categoryId);
    }

    @GetMapping("/clients/{id}/orders")
    public List<ClientOrder> getClientOrders(@PathVariable Long id) {
        return entitiesServiceImp.getClientOrders(id);
    }

    @GetMapping("/clients/{id}/products")
    public Set<Product> getClientProducts(@PathVariable Long id){
        return entitiesServiceImp.getClientProducts(id);
    }

    @GetMapping("/products/popular")
    public List<Product> getTopPopularProducts(@RequestParam Integer limit) {
        return entitiesServiceImp.getTopPopularProducts(limit);
    }
}
