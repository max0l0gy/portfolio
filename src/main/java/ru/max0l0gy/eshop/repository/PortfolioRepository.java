package ru.max0l0gy.eshop.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import ru.max0l0gy.eshop.entity.Portfolio;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PortfolioRepository implements PanacheRepository<Portfolio> {

}