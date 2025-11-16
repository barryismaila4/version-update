package com.agrotrack.agrotrack.controller;

import com.agrotrack.agrotrack.entity.Plant;
import com.agrotrack.agrotrack.service.PlantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/plants")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class PlantController {
    private final PlantService plantService;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    @GetMapping
    public ResponseEntity<List<Plant>> getUserPlants() {
        List<Plant> plants = plantService.getUserPlants();
        return ResponseEntity.ok(plants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plant> getPlant(@PathVariable Long id) {
        Optional<Plant> plant = plantService.getUserPlant(id);
        return plant.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Plant> createPlant(@RequestBody Plant plant) {
        Plant createdPlant = plantService.createPlant(plant);
        return ResponseEntity.ok(createdPlant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plant> updatePlant(@PathVariable Long id, @RequestBody Plant plant) {
        try {
            Plant updatedPlant = plantService.updatePlant(id, plant);
            return ResponseEntity.ok(updatedPlant);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlant(@PathVariable Long id) {
        try {
            plantService.deletePlant(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/water")
    public ResponseEntity<Plant> waterPlant(@PathVariable Long id) {
        try {
            Plant wateredPlant = plantService.waterPlant(id);
            return ResponseEntity.ok(wateredPlant);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getUserPlantsCount() {
        long count = plantService.getUserPlantsCount();
        return ResponseEntity.ok(count);
    }
}