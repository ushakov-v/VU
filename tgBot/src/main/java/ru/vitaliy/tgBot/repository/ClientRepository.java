package ru.vitaliy.tgBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.vitaliy.tgBot.entity.Client;

@RepositoryRestResource(collectionResourceRel = "clients", path = "clientss")
public interface ClientRepository extends JpaRepository<Client, Long> {

}