import { Component, OnInit } from '@angular/core';
import {KonvaModule} from "ng2-konva";
import {KonvaComponent} from "ng2-konva";
import { Konva } from "konva/cmj/_FullInternals";
import { Stage } from 'konva/cmj/Stage';
import {factoryShape} from "./ShapeFactory";


@Component({
  selector: 'app-product-consumer',
  templateUrl: './product-consumer.component.html',
  styleUrls: ['./product-consumer.component.css']
})
export class ProductConsumerComponent implements OnInit {

  stage:any;
  layer:any;
  shapeFactory :any =  new factoryShape();
  tr:any; 
  queueID : number = 1;
  machineID : number = 1;
  connection_mode : boolean = false;
  selected : any ;
  constructor() { }

  ngOnInit(): void {
    this.stage = new Konva.Stage({
      container: 'container',
      width: 1370,
      height: 600
    });
    this.layer = new Konva.Layer();
    this.stage.add(this.layer);
    this.tr = new Konva.Transformer({
      nodes: [],
      ignoreStroke: false,
      padding: 5,
      resizeEnabled: false,
      rotateEnabled: false,
      enabledAnchors: [],
    });
  }


  run(){

  }

  create_queue(){
    let queue = new Konva.Group({
      id: "queue" + '_' + (this.queueID.toString),
      x: 100,
      y: 100,
      draggable: true,
      name: "queue"
    });

    let konvaShape = this.shapeFactory.shapecreator('Q' , this.queueID.toString).get();
    queue.add(konvaShape);

    queue.add(new Konva.Text({
      text: "Q" + this.queueID + "\n" + "Products 0",
      fontSize: 20,
      fontFamily: 'Calibri',
      fontStyle: 'bold',
      x : 140,
      y : 140,
      offsetX: 33,
      offsetY: 20,
      align: 'center'
    }));

    this.layer.add(queue);
  }

  create_machine(){
    let machine = new Konva.Group({
      id: "machine" + '_' + (this.machineID.toString),
      x: 200,
      y: 200,
      draggable: true,
      name: "machine"
    });

    let konvaShape = this.shapeFactory.shapecreator('M' , this.queueID.toString).get();
    machine.add(konvaShape);

    machine.add(new Konva.Text({
      text: "M" + this.queueID + "\n" + "Available",
      fontSize: 20,
      fontFamily: 'Calibri',
      fontStyle: 'bold',
      x : 195,
      y : 195,
      offsetX: 33,
      offsetY: 20,
      align: 'center'
    }));

    this.layer.add(machine);
  }

  change_connection(){

  }

  create_connection(){

  }




}
