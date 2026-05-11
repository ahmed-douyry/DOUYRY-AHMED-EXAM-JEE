import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/Auth/auth-service';
import { Rental, RentalService } from '../../services/location/rental.service';
import { VehicleService } from '../../services/location/vehicle.service';

@Component({
  selector: 'app-rentals',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './rentals.html',
})
export class Rentals implements OnInit {
  private route = inject(ActivatedRoute);
  private rentalService = inject(RentalService);
  private vehicleService = inject(VehicleService);
  private fb = inject(FormBuilder);
  private router = inject(Router);
  auth = inject(AuthService);

  vehicleId!: number;
  rentals: Rental[] = [];
  error = '';
  form = this.fb.nonNullable.group({
    dateDebut: ['', Validators.required],
    dateFin: ['', Validators.required],
    locataire: ['', Validators.required],
  });

  ngOnInit(): void {
    this.vehicleId = Number(this.route.snapshot.paramMap.get('vehicleId'));
    this.load();
  }

  load(): void {
    this.error = '';
    this.rentalService.byVehicle(this.vehicleId).subscribe({
      next: (r) => (this.rentals = r),
      error: (e) => (this.error = e?.message ?? 'Erreur'),
    });
  }

  create(): void {
    if (this.form.invalid) return;
    this.rentalService.create(this.vehicleId, this.form.getRawValue()).subscribe({
      next: () => {
        this.form.reset();
        this.load();
      },
      error: (e) => (this.error = e?.error?.message ?? e?.message ?? 'Erreur location'),
    });
  }

  close(id: number): void {
    if (!this.auth.hasRole('ROLE_EMPLOYE') && !this.auth.hasRole('ROLE_ADMIN')) return;
    this.rentalService.close(id).subscribe({
      next: () => this.load(),
      error: (e) => (this.error = e?.message ?? 'Erreur cloture'),
    });
  }

  back(): void {
    history.back();
  }
}
