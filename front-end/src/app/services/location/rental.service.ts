import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface Rental {
  id: number;
  dateDebut: string;
  dateFin: string;
  montantTotal: number;
  locataire: string;
  vehicleId: number;
}

@Injectable({ providedIn: 'root' })
export class RentalService {
  private readonly api = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  byVehicle(vehicleId: number): Observable<Rental[]> {
    return this.http.get<Rental[]>(`${this.api}/vehicles/${vehicleId}/rentals`);
  }

  create(vehicleId: number, r: Partial<Rental>): Observable<Rental> {
    return this.http.post<Rental>(`${this.api}/vehicles/${vehicleId}/rentals`, r);
  }

  close(rentalId: number): Observable<Rental> {
    return this.http.put<Rental>(`${this.api}/rentals/${rentalId}/close`, {});
  }
}
