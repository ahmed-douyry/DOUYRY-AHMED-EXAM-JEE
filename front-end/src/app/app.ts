import { Component, OnInit, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AuthService } from './services/Auth/auth-service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.html',
})
export class App implements OnInit {
  protected readonly title = signal('LocaVehicules');
  constructor(private authservice: AuthService) {}
  ngOnInit(): void {
    this.authservice.loadToken();
  }
}
