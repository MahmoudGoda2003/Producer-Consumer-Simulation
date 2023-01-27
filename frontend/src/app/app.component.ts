import { Component } from '@angular/core';
import { DataService } from './Services/data.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Producer-Consumer';
  protected currentAction:string;

  constructor(private dataService:DataService){
    this.currentAction = 'machine';
  }

  ngOnInit() {
    this.dataService.getAction().subscribe((action) => this.currentAction = action);
  }
}
