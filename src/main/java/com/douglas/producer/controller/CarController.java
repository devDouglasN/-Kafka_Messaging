package com.douglas.producer.controller;

import com.douglas.producer.producer.CarProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarProducerService producerService;

    @PostMapping
    public ResponseEntity<CarDTO> create(@RequestBody CarDTO carDTO) {
        CarDTO car = CarDTO.builder().id(UUID.randomUUID().toString()).color(carDTO.getColor()).model(carDTO.getModel()).build();
        producerService.send(car);
        return  ResponseEntity.status(HttpStatus.CREATED).body(car);
    }
}
