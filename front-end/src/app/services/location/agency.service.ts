import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface Agency {
  id: number;
  nom: string;
  adresse: string;
  ville: string;
  telephone: string;
}

@Injectable({ providedIn: 'root' })
export class AgencyService {
  private readonly api = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  list(): Observable<Agency[]> {
    return this.http.get<Agency[]>(`${this.api}/agencies`);
  }

  search(keyword: string): Observable<Agency[]> {
    return this.http.get<Agency[]>(`${this.api}/agencies/search`, { params: { keyword } });
  }

  save(a: Partial<Agency>): Observable<Agency> {
    return this.http.post<Agency>(`${this.api}/agencies`, a);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/agencies/${id}`);
  }
}
