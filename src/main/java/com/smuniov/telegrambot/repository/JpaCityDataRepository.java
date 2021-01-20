package com.smuniov.telegrambot.repository;

import com.smuniov.telegrambot.entity.CityData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaCityDataRepository extends JpaRepository<CityData, Integer> {
    Optional<CityData> findByName(String name);
}
