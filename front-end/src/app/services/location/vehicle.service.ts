import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface Vehicle {
  id: number;
  marque: string;
  modele: string;
  matricule: string;
  prixParJour: number;
  dateMiseEnService: string;
  statut: string;
  vehicleKind: string;
  agencyId: number;
  nombrePortes?: number;
  typeCarburant?: string;
  boiteVitesse?: string;
  cylindree?: number;
  typeMoto?: string;
  casqueInclus?: boolean;
}

@Injectable({ providedIn: 'root' })
export class VehicleService {
  private readonly api = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  byAgency(agencyId: number): Observable<Vehicle[]> {
    return this.http.get<Vehicle[]>(`${this.api}/agencies/${agencyId}/vehicles`);
  }

  available(agencyId: number): Observable<Vehicle[]> {
    return this.http.get<Vehicle[]>(`${this.api}/agencies/${agencyId}/vehicles/available`);
  }

  saveCar(agencyId: number, v: Partial<Vehicle>): Observable<Vehicle> {
    return this.http.post<Vehicle>(`${this.api}/agencies/${agencyId}/vehicles/car`, v);
  }

  saveMoto(agencyId: number, v: Partial<Vehicle>): Observable<Vehicle> {
    return this.http.post<Vehicle>(`${this.api}/agencies/${agencyId}/vehicles/moto`, v);
  }

  updateStatus(vehicleId: number, statut: string): Observable<Vehicle> {
    return this.http.patch<Vehicle>(`${this.api}/vehicles/${vehicleId}/status`, {}, {
      params: { statut },
    });
  }
}
