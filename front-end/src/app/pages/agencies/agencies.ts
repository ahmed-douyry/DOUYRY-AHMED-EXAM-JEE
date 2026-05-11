import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Agency, AgencyService } from '../../services/location/agency.service';
import { AuthService } from '../../services/Auth/auth-service';

@Component({
  selector: 'app-agencies',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './agencies.html',
})
export class Agencies implements OnInit {
  private agencyService = inject(AgencyService);
  private fb = inject(FormBuilder);
  private router = inject(Router);
  auth = inject(AuthService);

  agencies: Agency[] = [];
  error = '';
  showForm = false;
  form = this.fb.nonNullable.group({
    nom: ['', Validators.required],
    adresse: ['', Validators.required],
    ville: ['', Validators.required],
    telephone: ['', Validators.required],
  });
  searchForm = this.fb.nonNullable.group({ keyword: [''] });

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.error = '';
    this.agencyService.list().subscribe({
      next: (a) => (this.agencies = a),
      error: (e) => (this.error = e?.message ?? 'Erreur chargement'),
    });
  }

  search(): void {
    const kw = this.searchForm.getRawValue().keyword.trim();
    this.error = '';
    if (!kw) {
      this.load();
      return;
    }
    this.agencyService.search(kw).subscribe({
      next: (a) => (this.agencies = a),
      error: (e) => (this.error = e?.message ?? 'Erreur recherche'),
    });
  }

  save(): void {
    if (this.form.invalid) return;
    this.agencyService.save(this.form.getRawValue()).subscribe({
      next: () => {
        this.form.reset();
        this.showForm = false;
        this.load();
      },
      error: (e) => (this.error = e?.message ?? 'Erreur enregistrement'),
    });
  }

  remove(id: number): void {
    if (!this.auth.hasRole('ROLE_ADMIN')) return;
    if (!confirm('Supprimer cette agence ?')) return;
    this.agencyService.delete(id).subscribe({
      next: () => this.load(),
      error: (e) => (this.error = e?.message ?? 'Suppression refusee'),
    });
  }

  goVehicles(id: number): void {
    this.router.navigate(['/admin/agencies', id, 'vehicles']);
  }
}
