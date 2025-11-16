import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Plant } from '../models/plant';
import { environment } from '../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class PlantService {
  private apiUrl = `${environment.apiUrl}/api/plants`;

  constructor(private http: HttpClient) {}

  getUserPlants(): Observable<Plant[]> {
    return this.http.get<Plant[]>(this.apiUrl);
  }

  getPlant(id: number): Observable<Plant> {
    return this.http.get<Plant>(`${this.apiUrl}/${id}`);
  }

  createPlant(plant: Plant): Observable<Plant> {
    return this.http.post<Plant>(this.apiUrl, plant);
  }

  updatePlant(id: number, plant: Plant): Observable<Plant> {
    return this.http.put<Plant>(`${this.apiUrl}/${id}`, plant);
  }

  deletePlant(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  waterPlant(id: number): Observable<Plant> {
    return this.http.post<Plant>(`${this.apiUrl}/${id}/water`, {});
  }

  getPlantsCount(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/count`);
  }
}