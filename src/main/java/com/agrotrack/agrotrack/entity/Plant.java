package com.agrotrack.agrotrack.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Plant {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Le nom de la plante est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom de la plante doit contenir entre 2 et 100 caractères")
    @Column(nullable = false)
    private String name;
    @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
    private String description;

    @Column(name = "plant_type")
    private String plantType;

    @Column(name = "planting_date")
    private LocalDateTime plantingDate;

    @Column(name = "last_watered")
    private LocalDateTime lastWatered;

    @Column(name = "watering_frequency_days")
    private Integer wateringFrequencyDays = 7;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Notification> notifications = new ArrayList<>();
    public Plant() {}

    public Plant(String name, String description, String plantType, User user) {
        this.name = name;
        this.description = description;
        this.plantType = plantType;
        this.user = user;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPlantType() { return plantType; }
    public void setPlantType(String plantType) { this.plantType = plantType; }

    public LocalDateTime getPlantingDate() { return plantingDate; }
    public void setPlantingDate(LocalDateTime plantingDate) { this.plantingDate = plantingDate; }

    public LocalDateTime getLastWatered() { return lastWatered; }
    public void setLastWatered(LocalDateTime lastWatered) { this.lastWatered = lastWatered; }

    public Integer getWateringFrequencyDays() { return wateringFrequencyDays; }
    public void setWateringFrequencyDays(Integer wateringFrequencyDays) { this.wateringFrequencyDays = wateringFrequencyDays; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<Notification> getNotifications() { return notifications; }
    public void setNotifications(List<Notification> notifications) { this.notifications = notifications; }
}


