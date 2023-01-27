import { Component, OnInit } from '@angular/core';
import { DataService } from 'src/app/Services/data.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {

  private newColor : string;
  private dataService:DataService;

  constructor(dataService : DataService) { 
    this.newColor = 'black';
    this.dataService = dataService
  }

  ngOnInit(): void {
  }

  addMachine(){
    this.dataService.setAction('machine');
  }

  addQueue(){
    this.dataService.setAction('queue');
  }

  addConnection(){
    this.dataService.setAction('connection');
  }

  selectComponent(){
    this.dataService.setAction('selection');
  }

  deleteComponent(){
  }
  deleteAll(){
    
  }
  startSimulation(){}

  pauseSimulation(){}
}
