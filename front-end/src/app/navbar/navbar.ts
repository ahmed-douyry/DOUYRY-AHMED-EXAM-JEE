import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../services/Auth/auth-service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink, RouterLinkActive, CommonModule],
  templateUrl: './navbar.html',
})
export class Navbar {
  constructor(public authService: AuthService, private router: Router) {}

  handleLogout() {
    this.authService.logout();
    this.router.navigateByUrl('/login');
  }
}
