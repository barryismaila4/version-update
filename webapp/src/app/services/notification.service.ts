import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Notification } from '../models/notification';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private apiUrl = `${environment.apiUrl}/api/notifications`;

  constructor(private http: HttpClient) {}

  getUserNotifications(): Observable<Notification[]> {
    return this.http.get<Notification[]>(this.apiUrl);
  }

  getUnreadNotifications(): Observable<Notification[]> {
    return this.http.get<Notification[]>(`${this.apiUrl}/unread`);
  }

  createNotification(notification: any, plantId?: number): Observable<Notification> {
    const params: any = {};
    if (plantId && plantId !== 0) {
      params.plantId = plantId.toString();
    }

    // Envoie seulement les données nécessaires
    const notificationData = {
      title: notification.title,
      description: notification.description,
      notificationDate: notification.notificationDate,
      notificationType: notification.notificationType || 'RAPPEL',
      isRead: false
    };

    return this.http.post<Notification>(this.apiUrl, notificationData, { params });
  }
  markAsRead(id: number): Observable<Notification> {
    return this.http.put<Notification>(`${this.apiUrl}/${id}/read`, {});
  }

  deleteNotification(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getUnreadCount(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/count/unread`);
  }

  getPlantNotifications(plantId: number): Observable<Notification[]> {
    return this.http.get<Notification[]>(`${this.apiUrl}/plant/${plantId}`);
  }
}