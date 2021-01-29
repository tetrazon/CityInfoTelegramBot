package com.smuniov.telegrambot.controller;

import com.smuniov.telegrambot.entity.CityData;
import com.smuniov.telegrambot.exceptions.BadDataException;
import com.smuniov.telegrambot.repository.JpaCityDataRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class MainController {

    private final JpaCityDataRepository cityDataRepository;

    public MainController(JpaCityDataRepository cityDataRepository) {
        this.cityDataRepository = cityDataRepository;
    }

    @GetMapping
    public List<CityData> list(){return cityDataRepository.findAll();}

    @GetMapping("{id}")
    public ResponseEntity<CityData> getOne(@PathVariable("id") int id) {
        Optional<CityData> optionalCityData = cityDataRepository.findById(id);
        return optionalCityData.map(cityData ->
                ResponseEntity.status(HttpStatus.OK).body(cityData)).orElseGet(() ->
                                            ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public CityData create(@RequestBody CityData cityData){
        if (cityData == null) {
            throw new BadDataException("Wrong data, city name and it's description must not be null");
        }
        if (cityDataRepository.findByName(cityData.getName()).isPresent()){
            throw new BadDataException("Already exists");
        }
        return cityDataRepository.save(cityData);
    }

    @PutMapping("{id}" )
    public CityData update(@PathVariable("id") CityData cityData, @RequestBody CityData newCityData) {
        BeanUtils.copyProperties(newCityData, cityData, "id");
        return cityDataRepository.save(cityData);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") int id) {
        if(!cityDataRepository.existsById(id)) {
            throw new BadDataException("No element with id = " + id);
        }
        cityDataRepository.deleteById(id);
    }

}
