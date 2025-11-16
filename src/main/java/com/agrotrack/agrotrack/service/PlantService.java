package com.agrotrack.agrotrack.service;

import com.agrotrack.agrotrack.entity.Plant;
import com.agrotrack.agrotrack.entity.User;
import com.agrotrack.agrotrack.repository.PlantRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PlantService {
    private final PlantRepository plantRepository;
    private final UserService userService;

    public PlantService(PlantRepository plantRepository, UserService userService) {
        this.plantRepository = plantRepository;
        this.userService = userService;
    }

    public List<Plant> getUserPlants() {
        Long userId = userService.getCurrentUserId();
        return plantRepository.findByUserId(userId);
    }

    public Optional<Plant> getUserPlant(Long plantId) {
        Long userId = userService.getCurrentUserId();
        return plantRepository.findByIdAndUserId(plantId, userId);
    }

    public Plant createPlant(Plant plant) {
        Long userId = userService.getCurrentUserId();
        Optional<User> user = userService.getCurrentUser();

        if (user.isPresent()) {
            plant.setUser(user.get());
            plant.setCreatedAt(LocalDateTime.now());
            plant.setUpdatedAt(LocalDateTime.now());
            if (plant.getPlantingDate() == null) {
                plant.setPlantingDate(LocalDateTime.now());
            }
            return plantRepository.save(plant);
        }
        throw new RuntimeException("Utilisateur non trouvé");
    }

    public Plant updatePlant(Long plantId, Plant plantDetails) {
        Long userId = userService.getCurrentUserId();
        Optional<Plant> plant = plantRepository.findByIdAndUserId(plantId, userId);

        if (plant.isPresent()) {
            Plant existingPlant = plant.get();
            existingPlant.setName(plantDetails.getName());
            existingPlant.setDescription(plantDetails.getDescription());
            existingPlant.setPlantType(plantDetails.getPlantType());
            existingPlant.setWateringFrequencyDays(plantDetails.getWateringFrequencyDays());
            existingPlant.setUpdatedAt(LocalDateTime.now());

            return plantRepository.save(existingPlant);
        }
        throw new RuntimeException("Plante non trouvée");
    }

    public void deletePlant(Long plantId) {
        Long userId = userService.getCurrentUserId();
        Optional<Plant> plant = plantRepository.findByIdAndUserId(plantId, userId);

        if (plant.isPresent()) {
            plantRepository.deleteById(plantId);
        } else {
            throw new RuntimeException("Plante non trouvée");
        }
    }

    public Plant waterPlant(Long plantId) {
        Long userId = userService.getCurrentUserId();
        Optional<Plant> plant = plantRepository.findByIdAndUserId(plantId, userId);

        if (plant.isPresent()) {
            Plant existingPlant = plant.get();
            existingPlant.setLastWatered(LocalDateTime.now());
            existingPlant.setUpdatedAt(LocalDateTime.now());
            return plantRepository.save(existingPlant);
        }
        throw new RuntimeException("Plante non trouvée");
    }

    public long getUserPlantsCount() {
        Long userId = userService.getCurrentUserId();
        return plantRepository.countByUserId(userId);
    }

}