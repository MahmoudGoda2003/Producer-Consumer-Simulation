import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class DataService {
  private action = new BehaviorSubject('');
  private action$ = this.action.asObservable();
  
  constructor() { }

  getAction():Observable<string> {
    return this.action$;
}

  setAction(action : string) {
      this.action.next(action);
  }
}
