import { Component, OnInit } from '@angular/core';
import { PlantService } from '../../services/plant.service';
import { NotificationService } from '../../services/notification.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  username = '';

  constructor() {}

  ngOnInit() {
    this.username = localStorage.getItem('username') || '';
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    window.location.href = '/auth/login';
  }
}