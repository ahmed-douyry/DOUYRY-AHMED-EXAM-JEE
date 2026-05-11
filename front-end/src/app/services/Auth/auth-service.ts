import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http:  HttpClient) {
    this.loadToken();
  }
  isAuthenticated :boolean = false;
  roles = '';
  uaername:any;
  accesTokken = '';

  loadToken() {
    if (typeof window !== 'undefined') {
      const token = window.localStorage.getItem('jwt-token');
      if (token) {
        this.accesTokken = token;
        let decodedjwt = atob(token.split('.')[1]);
        let jwtData = JSON.parse(decodedjwt);
        this.roles = jwtData.scope || '';
        this.uaername = jwtData.sub;
        this.isAuthenticated = true;
      }
    }
  }

  login(username: string, password: string) {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post('http://localhost:8080/auth/login', { username, password }, options);
  }
loadProfile(data:any){
    this.accesTokken = data['accessToken'];
    let decodedjwt = atob(this.accesTokken.split('.')[1]);
    let jwtData = JSON.parse(decodedjwt);
  this.roles = jwtData.scope || '';
  this.uaername = jwtData.sub;
  this.isAuthenticated = true;
  if (typeof window !== 'undefined') {
    window.localStorage.setItem('jwt-token', this.accesTokken as string);
  }

}

  logout() {
    this.isAuthenticated = false;
    this.accesTokken = '';
    this.roles = '';
    this.uaername = null;
    if (typeof window !== 'undefined') {
      window.localStorage.removeItem('jwt-token');
    }
  }

  hasRole(role: string): boolean {
    return typeof this.roles === 'string' && this.roles.includes(role);
  }
  
}
