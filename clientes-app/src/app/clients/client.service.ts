import { Injectable } from '@angular/core';
import { CLIENTS } from './clients.json';
import { Client } from './client';
import { of, Observable} from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable()

export class ClientService {

  private urlEndPoint: string = 'http://localhost:8080/api/clients'
  private httpheaders = new HttpHeaders({'Content-Type': 'application/json'})

  constructor(private http: HttpClient) { }

  getClients(): Observable<Client[]>{
    //return of(CLIENTS)
    return this.http.get<Client[]>(this.urlEndPoint);
  }

  create(client: Client): Observable<Client> {
    return this.http.post<Client>(this.urlEndPoint, client, {headers: this.httpheaders})
  }

  getClient(id): Observable<Client>{
    return this.http.get<Client>(`${this.urlEndPoint}/${id}`)
  }

  update(client: Client): Observable<Client>{
    return this.http.put<Client>(`${this.urlEndPoint}/${client.id}`, client, {headers: this.httpheaders})
  }

  delete(id: number): Observable<Client>{
    return this.http.delete<Client>(`${this.urlEndPoint}/${id}`, {headers: this.httpheaders})
  }
}
