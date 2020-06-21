import { Injectable } from '@angular/core';
import { CLIENTS } from './clients.json';
import { Client } from './client';
import { catchError, map } from 'rxjs/operators';
import { of, Observable, throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import swal from 'sweetalert2';

import { Router } from '@angular/router'

@Injectable()

export class ClientService {

  private urlEndPoint: string = 'http://localhost:8080/api/clients'
  private httpheaders = new HttpHeaders({'Content-Type': 'application/json'})

  constructor(private http: HttpClient, private router: Router) { }

  getClients(): Observable<Client[]>{
    //return of(CLIENTS)
    return this.http.get<Client[]>(this.urlEndPoint);
  }

  create(client: Client): Observable<any> {
    return this.http.post<any>(this.urlEndPoint, client, {headers: this.httpheaders}).pipe(
      catchError(e => {
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    )
  }

  getClient(id): Observable<Client>{
    return this.http.get<Client>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e => {
        this.router.navigate(['/clients']);
        console.error(e.error.mensaje)
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);

      })
    )
  }

  update(client: Client): Observable<Client>{
    return this.http.put(`${this.urlEndPoint}/${client.id}`, client, {headers: this.httpheaders}).pipe(
      map( (response: any ) => response.cliente as Client),
      catchError(e => {
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    )
  }

  delete(id: number): Observable<Client>{
    return this.http.delete<Client>(`${this.urlEndPoint}/${id}`, {headers: this.httpheaders}).pipe(
      catchError(e => {
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    )
  }
}
