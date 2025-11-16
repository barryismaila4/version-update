import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Plant } from '../../models/plant';
import { PlantService } from '../../services/plant.service';
import { NotificationService } from '../../services/notification.service';

@Component({
  selector: 'app-plants',
  templateUrl: './plants.component.html',
  styleUrls: ['./plants.component.css']
})
export class PlantsComponent implements OnInit {
  plants: Plant[] = [];
  selectedPlant: Plant | null = null;
  showForm = false;
  editingPlant: Plant | null = null;
  newPlant: Plant = {
    id: 0,
    name: '',
    description: '',
    plantType: '',
    user: undefined
  };

  constructor(
    private plantService: PlantService,
    private notificationService: NotificationService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loadPlants();
  }

  loadPlants() {
    this.plantService.getUserPlants().subscribe(plants => {
      this.plants = plants;
      // Charge les notifications pour chaque plante
      this.plants.forEach(plant => {
        if (plant.id) {
          this.notificationService.getPlantNotifications(plant.id).subscribe(notifications => {
            plant.notifications = notifications;
          });
        }
      });
    });
  }

  showPlantDetails(plant: Plant) {
    this.selectedPlant = plant;
  }

  showAddForm() {
    this.showForm = true;
    this.editingPlant = null;
    this.newPlant = {
      id: 0,
      name: '',
      description: '',
      plantType: '',
      user: undefined
    };
  }

  showEditForm(plant: Plant) {
    this.showForm = true;
    this.editingPlant = plant;
    this.newPlant = { ...plant };
  }

  savePlant() {
    if (this.editingPlant) {
      this.plantService.updatePlant(this.editingPlant.id, this.newPlant).subscribe(() => {
        this.loadPlants();
        this.cancelForm();
      });
    } else {
      this.plantService.createPlant(this.newPlant).subscribe(() => {
        this.loadPlants();
        this.cancelForm();
      });
    }
  }

  deletePlant(id: number) {
    if (confirm('Supprimer cette plante?')) {
      this.plantService.deletePlant(id).subscribe(() => {
        this.loadPlants();
        this.selectedPlant = null;
      });
    }
  }

  waterPlant(id: number) {
    this.plantService.waterPlant(id).subscribe(() => {
      this.loadPlants();
      if (this.selectedPlant?.id === id) {
        this.selectedPlant.lastWatered = new Date().toISOString();
      }
    });
  }

  addNotificationToPlant(plant: Plant) {
    this.router.navigate(['/dashboard/notifications'], {
      queryParams: { plantId: plant.id }
    });
  }

  markNotificationAsRead(notificationId: number) {
    this.notificationService.markAsRead(notificationId).subscribe(() => {
      this.loadPlants();
    });
  }

  cancelForm() {
    this.showForm = false;
    this.editingPlant = null;
    this.newPlant = {
      id: 0,
      name: '',
      description: '',
      plantType: '',
      user: undefined
    };
  }
}