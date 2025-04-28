package com.example.currencytracking.controller;

import com.example.currencytracking.entity.CurrencyEntity;
import com.example.currencytracking.repository.CurrencyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {

    private final CurrencyRepository repository;

    public CurrencyController(CurrencyRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<CurrencyEntity>> getCurrencies() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<Void> addCurrency(@RequestBody CurrencyEntity request) {
        request.setId(UUID.randomUUID());
        repository.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyEntity> getCurrency(@PathVariable UUID id) {
        Optional<CurrencyEntity> currency = repository.findById(id);
        return currency.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCurrency(@PathVariable UUID id, @RequestBody CurrencyEntity request) {
        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        request.setId(id);
        repository.save(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable UUID id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}