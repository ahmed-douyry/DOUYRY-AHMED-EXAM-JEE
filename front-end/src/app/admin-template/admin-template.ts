import { Component } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { Navbar } from '../navbar/navbar';

@Component({
  selector: 'app-admin-template',
  imports: [RouterOutlet, Navbar],
  templateUrl: './admin-template.html',
})
export class AdminTemplate {}
