import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Network } from '../Shared/network';

@Injectable({
  providedIn: 'root'
})
export class APIService {

  private url:string = 'http://localhost:8080/Produce/';

  constructor(private http: HttpClient) {  }

  startSimulation(productsNo:number, machines:string[], queues:string[], fromConnectors:string[], toConnectors:string[]):Observable<string> {
    return this.http.post<string>(this.url + 'Simulate', {"productsNo": productsNo, "machines":machines, "queues": queues, "fromConnectors":fromConnectors, "toConnectors":toConnectors});
  }

  restartSimulation():Observable<string> {
    return this.http.get<string>(this.url + 'Replay');
  }

  pauseSimulation():Observable<string> {
    return this.http.get<string>(this.url + 'Pause');
  }

  polling(): Observable<Network> {
    return this.http.get<Network>(this.url + 'Poll');
  }

  Play(queueID : string[], machineID : string[]  , to : string[] ,from : string[] , products : number ){
    return this.http.post<object>(this.url + 'Play', {"productsNo":products,"machines":machineID,"queues":queueID,"FromConnectors":from,"toConnectors":to});
  }
}
