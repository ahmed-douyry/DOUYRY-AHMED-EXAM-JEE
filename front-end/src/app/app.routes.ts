import { Routes } from '@angular/router';
import { athenticationGuard } from './guards/athentication-guard';

export const routes: Routes = [
  { path: 'login', loadComponent: () => import('./login/login').then((m) => m.Login) },
  { path: '', redirectTo: '/admin', pathMatch: 'full' },
  {
    path: 'admin',
    loadComponent: () => import('./admin-template/admin-template').then((m) => m.AdminTemplate),
    // canActivate: [athenticationGuard],
    children: [
      { path: '', redirectTo: 'agencies', pathMatch: 'full' },
      { path: 'agencies', loadComponent: () => import('./pages/agencies/agencies').then((m) => m.Agencies) },
      {
        path: 'agencies/:agencyId/vehicles',
        loadComponent: () => import('./pages/vehicles/vehicles').then((m) => m.Vehicles),
      },
      {
        path: 'vehicles/:vehicleId/rentals',
        loadComponent: () => import('./pages/rentals/rentals').then((m) => m.Rentals),
      },
      {
        path: 'not-authorised',
        loadComponent: () => import('./not-authorised/not-authorised').then((m) => m.NotAuthorised),
      },
    ],
  },
];
