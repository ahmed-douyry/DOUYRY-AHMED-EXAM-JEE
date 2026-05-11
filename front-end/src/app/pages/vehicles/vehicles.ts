import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/Auth/auth-service';
import { Vehicle, VehicleService } from '../../services/location/vehicle.service';

@Component({
  selector: 'app-vehicles',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './vehicles.html',
})
export class Vehicles implements OnInit {
  private route = inject(ActivatedRoute);
  private vehicleService = inject(VehicleService);
  private fb = inject(FormBuilder);
  private router = inject(Router);
  auth = inject(AuthService);

  agencyId!: number;
  vehicles: Vehicle[] = [];
  error = '';
  carForm = this.fb.nonNullable.group({
    marque: ['', Validators.required],
    modele: ['', Validators.required],
    matricule: ['', Validators.required],
    prixParJour: [0, [Validators.required, Validators.min(1)]],
    dateMiseEnService: ['', Validators.required],
    nombrePortes: [5, Validators.required],
    typeCarburant: ['ESSENCE', Validators.required],
    boiteVitesse: ['MANUELLE', Validators.required],
  });
  motoForm = this.fb.nonNullable.group({
    marque: ['', Validators.required],
    modele: ['', Validators.required],
    matricule: ['', Validators.required],
    prixParJour: [0, [Validators.required, Validators.min(1)]],
    dateMiseEnService: ['', Validators.required],
    cylindree: [125, Validators.required],
    typeMoto: ['SCOOTER', Validators.required],
    casqueInclus: [true],
  });

  ngOnInit(): void {
    this.agencyId = Number(this.route.snapshot.paramMap.get('agencyId'));
    this.load();
  }

  load(): void {
    this.error = '';
    this.vehicleService.byAgency(this.agencyId).subscribe({
      next: (v) => (this.vehicles = v),
      error: (e) => (this.error = e?.message ?? 'Erreur'),
    });
  }

  saveCar(): void {
    if (this.carForm.invalid) return;
    const raw = this.carForm.getRawValue();
    this.vehicleService.saveCar(this.agencyId, { ...raw, prixParJour: raw.prixParJour }).subscribe({
      next: () => {
        this.carForm.reset({ nombrePortes: 5, typeCarburant: 'ESSENCE', boiteVitesse: 'MANUELLE', prixParJour: 0 });
        this.load();
      },
      error: (e) => (this.error = e?.message ?? 'Erreur'),
    });
  }

  saveMoto(): void {
    if (this.motoForm.invalid) return;
    const raw = this.motoForm.getRawValue();
    this.vehicleService.saveMoto(this.agencyId, { ...raw, prixParJour: raw.prixParJour, casqueInclus: raw.casqueInclus }).subscribe({
      next: () => {
        this.motoForm.reset({ cylindree: 125, typeMoto: 'SCOOTER', casqueInclus: true, prixParJour: 0 });
        this.load();
      },
      error: (e) => (this.error = e?.message ?? 'Erreur'),
    });
  }

  setStatus(v: Vehicle, statut: string): void {
    this.vehicleService.updateStatus(v.id, statut).subscribe({
      next: () => this.load(),
      error: (e) => (this.error = e?.message ?? 'Erreur'),
    });
  }

  goRentals(id: number): void {
    this.router.navigate(['/admin/vehicles', id, 'rentals']);
  }

  back(): void {
    this.router.navigate(['/admin/agencies']);
  }
}
