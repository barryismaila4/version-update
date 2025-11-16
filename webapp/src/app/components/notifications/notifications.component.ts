import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Notification } from 'src/app/models/notification';
import { NotificationService } from '../../services/notification.service';
import { PlantService } from '../../services/plant.service';
import { Plant } from '../../models/plant';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {
  notifications: Notification[] = [];
  plants: Plant[] = [];
  showForm = false;
  newNotification: Notification = {
    id: 0,
    title: '',
    description: '',
    notificationDate: new Date().toISOString(),
    isRead: false,
    user: undefined,
    plant: undefined
  };
  selectedPlantId: number = 0;

  constructor(
    private notificationService: NotificationService,
    private plantService: PlantService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.loadNotifications();
    this.loadPlants();

    // Récupère l'ID de plante depuis l'URL si présent
    this.route.queryParams.subscribe(params => {
      if (params['plantId']) {
        this.selectedPlantId = +params['plantId'];
      }
    });
  }

  loadNotifications() {
    this.notificationService.getUserNotifications().subscribe(notifications => {
      this.notifications = notifications;
    });
  }

  loadPlants() {
    this.plantService.getUserPlants().subscribe(plants => {
      this.plants = plants;
    });
  }

  showAddForm() {
    this.showForm = true;
    this.newNotification = {
      id: 0,
      title: '',
      description: '',
      notificationDate: new Date().toISOString(),
      isRead: false,
      user: undefined,
      plant: undefined
    };
  }

  createNotification() {
    const notificationData = {
      title: this.newNotification.title,
      description: this.newNotification.description,
      notificationDate: this.newNotification.notificationDate,
      notificationType: 'RAPPEL'
    };

    const plantId = this.selectedPlantId === 0 ? undefined : this.selectedPlantId;

    this.notificationService.createNotification(notificationData, plantId)
      .subscribe({
        next: () => {
          this.loadNotifications();
          this.cancelForm();
        },
        error: (error) => {
          console.error('Erreur création notification:', error);
          alert('Erreur: ' + (error.error?.message || 'Création échouée'));
        }
      });
  }

  markAsRead(id: number) {
    this.notificationService.markAsRead(id).subscribe(() => {
      this.loadNotifications();
    });
  }

  deleteNotification(id: number) {
    if (confirm('Supprimer cette notification?')) {
      this.notificationService.deleteNotification(id).subscribe(() => {
        this.loadNotifications();
      });
    }
  }

  cancelForm() {
    this.showForm = false;
    this.selectedPlantId = 0;
  }
}