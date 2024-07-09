package ru.vitaliy.tgBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.vitaliy.tgBot.entity.Product;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);

    Optional<Product> findById(Long id);

    Product findByName(String филадельфия);
}
