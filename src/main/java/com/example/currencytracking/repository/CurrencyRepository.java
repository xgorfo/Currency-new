package com.example.currencytracking.repository;

import com.example.currencytracking.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CurrencyRepository extends JpaRepository<CurrencyEntity, UUID> {
}