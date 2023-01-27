import { Component, HostListener, Input, OnInit, SimpleChanges } from '@angular/core';
import Konva from 'konva';
import { v4 as uuidv4 } from 'uuid';
import { APIService } from 'src/app/Services/api.service';
import { PlatformModule } from '@angular/cdk/platform';
import { DataService } from 'src/app/Services/data.service';

@Component({
  selector: 'app-factory',
  templateUrl: './Factory.component.html',
  styleUrls: ['./Factory.component.css']
})
export class FactoryComponent implements OnInit {
  @Input() currentAction:string;
  private stage!:Konva.Stage;
  private layer:Konva.Layer;
  private machine:Konva.Circle;
  private connection:Konva.Arrow;
  private newConnection:Konva.Arrow;
  private selectionRectangle:Konva.Rect;
  private tr:Konva.Transformer;
  private queue:Konva.Rect;
  private stack:Konva.Ellipse;
  private load:Konva.Ellipse;
  private stackGroup:Konva.Group;
  private loadGroup:Konva.Group;
  private container!:HTMLElement;
  private startComponent!:Konva.Node;
  private endComponent!:Konva.Node;
  private makeConnection:boolean;
  private currentComponent!:Konva.Node;
  private selectComponent:boolean;
  private machines:string[];
  private queues:string[];
  private toConnectors:string[];
  private fromConnectors:string[];
  private machineCount:number;
  private queueCount:number;
  private availableMachines:number[];
  private availableQueues:number[];
  private products : number;
  private simulation: any;
  
  constructor(private dataService: DataService, private apiService : APIService) {
    this.simulation = '';
    this.currentAction = '';
    this.products = 0;
    this.layer = new Konva.Layer();

    this.machine = new Konva.Circle({
      radius: 28,
      fill: 'grey',
      stroke: 'black',
      strokeWidth: 0
    });
  
    this.queue = new Konva.Rect({
      width: 48,
      height: 48,
      fill: 'grey',
      stroke: 'black',
      strokeWidth: 0
    });

    this.stack = new Konva.Ellipse({
      id: 'stack',
      x: 46,
      y: 280,
      radiusX: 34,
      radiusY: 22,
      fill: 'grey',
      stroke: 'black',
      strokeWidth: 0
    });

    this.stackGroup = new Konva.Group({
      name: 'queue',
      id: 'stack',
      width: 68,
      height: 44
    });

    this.stackGroup.add(this.stack);
    this.stackGroup.add(new Konva.Text({
      text: "Stack" + "\n" + "products 0",
      fontSize: 10,
      fontFamily: 'Calibri',
      fontStyle: 'bold',
      x : 24,
      y : 268,
      align: 'center'
    }));

    this.stackGroup.setAttr('id', 'Q' + uuidv4().toString());
    this.stackGroup.setAttr('setX', this.stack.getAttr('x'));
    this.stackGroup.setAttr('setY', this.stack.getAttr('y'));

    this.load = new Konva.Ellipse({
      x: 1384,
      y: 280,
      radiusX: 34,
      radiusY: 22,
      fill: 'grey',
      stroke: 'black',
      strokeWidth: 0
    });

    this.loadGroup = new Konva.Group({
      name: 'queue',
      id: 'load',
      width: 68,
      height: 44
    });

    this.loadGroup.add(this.load);
    this.loadGroup.add(new Konva.Text({
      text: "Load" + "\n" + "products 0",
      fontSize: 10,
      fontFamily: 'Calibri',
      fontStyle: 'bold',
      x : 1362,
      y : 268,
      align: 'center'
    }));

    this.loadGroup.setAttr('id', 'Q' + uuidv4().toString());
    this.loadGroup.setAttr('setX', this.load.getAttr('x'));
    this.loadGroup.setAttr('setY', this.load.getAttr('y'));

    this.connection = new Konva.Arrow({
      name: "connector",
      points: [],
      pointerLength: 10,
      pointerWidth: 10,
      fill: 'grey',
      stroke: 'grey',
      strokeWidth: 3
    });

    this.newConnection = this.connection;

    this.selectionRectangle = new Konva.Rect({
      width: 1,
      height: 1,
      visible: false
    });

    this.tr = new Konva.Transformer({
      anchorStroke: 'grey',
      anchorFill: 'white',
      anchorSize: 0,
      borderStroke: 'black',
      borderDash: [3, 3],
      shouldOverdrawWholeArea: true
    });
    this.tr.rotateEnabled(false);

    this.makeConnection = false;
    this.selectComponent = false;

    this.machines = [];
    this.queues = [];
    this.fromConnectors = [];
    this.toConnectors = [];
    this.availableMachines = [];
    this.availableQueues = [];
    
    this.machineCount = 1;
    this.queueCount = 1;
    this.products = 0;
  }

  ngOnInit(): void {
    this.container = document.getElementById('factory')!;
    this.stage = new Konva.Stage({
      container: 'factory',
      width: this.container.offsetWidth,
      height: this.container.offsetHeight

    });
    
    this.queues.push(this.stackGroup.getAttr('id'));
    this.queues.push(this.loadGroup.getAttr('id'));

    this.layer.add(this.stackGroup);
    this.layer.add(this.loadGroup);
    this.layer.add(this.tr);
    this.stage.add(this.layer);
    this.layer.batchDraw();
  }


    ngOnChanges(changes: SimpleChanges) {
      if (changes.hasOwnProperty('currentAction')) {
        let machineButton = document.getElementById('addMachine')!;
        let queueButton = document.getElementById('addQueue')!; 
        let connectorButton = document.getElementById('addConnector')!;
        let selectButton = document.getElementById('select')!;
        switch(this.currentAction) {
          case 'machine':
            machineButton.style.background = 'grey';
            queueButton.style.background = '#3C3D3E';
            connectorButton.style.background = '#3C3D3E';
            selectButton.style.background = '#3C3D3E';
            break;
          case 'queue':
            machineButton.style.background = '#3C3D3E';
            queueButton.style.background = 'grey';
            connectorButton.style.background = '#3C3D3E';
            selectButton.style.background = '#3C3D3E';
            break;
          case 'connection':
            machineButton.style.background = '#3C3D3E';
            queueButton.style.background = '#3C3D3E';
            connectorButton.style.background = 'grey';
            selectButton.style.background = '#3C3D3E';
            break;
          case 'selection':
            machineButton.style.background = '#3C3D3E';
            queueButton.style.background = '#3C3D3E';
            connectorButton.style.background = '#3C3D3E';
            selectButton.style.background = 'grey';
            break;
          default:
        }
      }
    }
  

  @HostListener('mousedown') onMouseDown() {
    if (this.currentAction != 'connection' && this.currentAction != 'selection'){
      return;
    }
    
    let pos = this.stage.getPointerPosition()!;
    this.selectionRectangle.setAttrs({
      x: pos.x,
      y: pos.y,
    });

    let shapes = this.layer.find('.machine');
    shapes = shapes.concat(this.layer.find('.queue'));
    shapes = shapes.concat(this.layer.find('.connector'));
    let box = this.selectionRectangle.getClientRect();
    let selected = shapes?.filter((shape) =>
        Konva.Util.haveIntersection(box, shape.getClientRect())
    );

    this.tr.nodes([]);

    if (selected![0] === undefined)
      return;

    if (this.currentAction === 'connection'){
      this.makeConnection = true;
      this.newConnection = this.connection.clone();
      this.startComponent = selected[0];

      this.newConnection.setAttr('points', [pos.x, pos.y, pos.x, pos.y]);
      this.layer.add(this.newConnection);

      this.layer.batchDraw();
    } else {
      if (selected[0] === this.stackGroup || selected[0] === this.loadGroup)
        return;

      this.selectComponent = true;
      this.currentComponent = selected[0];
      this.tr.nodes([this.currentComponent]);
    }
  }

  @HostListener('mousemove') onMouseMove() {
    if (!this.makeConnection)
      return;

    let pos = this.stage.getPointerPosition()!;
    let newPoints = this.newConnection.points()
    newPoints[2] = pos.x;
    newPoints[3] = pos.y;
    this.newConnection.points(newPoints);

    this.layer.batchDraw;
  }

  @HostListener('mouseup') onMouseUp() {
    if (!this.makeConnection)
      return;

    let pos = this.stage.getPointerPosition()!;
    this.selectionRectangle!.setAttrs({
      x: pos.x,
      y: pos.y,
    });

    let shapes = this.layer.find('.machine');
    shapes = shapes.concat(this.layer.find('.queue'));
    let box = this.selectionRectangle.getClientRect();
    let selected = shapes?.filter((shape) =>
        Konva.Util.haveIntersection(box, shape.getClientRect())
    );

    if (selected![0] === undefined || selected[0].getAttr('id') === this.startComponent.getAttr('id')) {
      this.newConnection.destroy();
      return;
    }

    this.endComponent = selected[0];

    this.newConnection.points(this.drawConnector(this.startComponent, this.endComponent));
    this.newConnection.setAttr('id', 'C' + uuidv4().toString());
    this.newConnection.setAttr('fill', 'black');
    this.newConnection.setAttr('stroke', 'black');
    this.newConnection.setAttr('from', this.startComponent.getAttr('id'));
    this.newConnection.setAttr('to', this.endComponent.getAttr('id'));
    this.makeConnection = false;

    this.fromConnectors.push(this.startComponent.getAttr('id'));
    this.toConnectors.push(this.endComponent.getAttr('id'));
    this.layer.batchDraw();
  }

  drawConnector(from: Konva.Node, to: Konva.Node) {
    const dx = to.getAttr('setX') - from.getAttr('setX');
    const dy = to.getAttr('setY') - from.getAttr('setY');
    let angle = Math.atan2(-dy, dx);

    const radius = 36;

    return [
      from.getAttr('setX') + -radius * Math.cos(angle + Math.PI),
      from.getAttr('setY') + radius * Math.sin(angle + Math.PI),
      to.getAttr('setX') + -radius * Math.cos(angle),
      to.getAttr('setY') + radius * Math.sin(angle),
    ];
  }

  deleteComponent(){
    if (!this.selectComponent || this.currentComponent === this.stackGroup || this.currentComponent === this.loadGroup)
      return;

    if (this.currentComponent.getAttr('name') === 'connector'){
      for (let i = 0; i < this.fromConnectors.length; i++) {
        if (this.fromConnectors[i] === this.currentComponent.getAttr('from') && this.toConnectors[i] === this.currentComponent.getAttr('to')){
          delete this.fromConnectors[i];
          delete this.toConnectors[i];
          break;
        }
      }
      this.fromConnectors = this.fromConnectors.filter(n => n);
      this.toConnectors = this.toConnectors.filter(n => n);
    } else {
      if (this.currentComponent.getAttr('name') === 'machine') {
        for (let i = 0; i < this.machines.length; i++) {
          if (this.machines[i] === this.currentComponent.getAttr('id')){
            delete this.machines[i];
            break;
          }
        }
        this.availableMachines.push(this.currentComponent.getAttr('number'));
        this.availableMachines.sort(function(a, b){return a - b});
        this.machines = this.machines.filter(n => n);
      } else if (this.currentComponent.getAttr('name') === 'queue') {
        for (let i = 0; i < this.queues.length; i++) {
          if (this.queues[i] === this.currentComponent.getAttr('id')){
            delete this.queues[i];
            break;
          }
        }
        this.availableQueues.push(this.currentComponent.getAttr('number'));
        this.availableQueues.sort(function(a, b){return a - b});
        this.queues = this.queues.filter(n => n);
      }
      for (let i = 0; i < this.fromConnectors.length; i++) {
        if (this.fromConnectors[i] === this.currentComponent.getAttr('id') || this.toConnectors[i] === this.currentComponent.getAttr('id')){
          delete this.fromConnectors[i];
          delete this.toConnectors[i];
        }
      }

      let attachedConnectors = this.layer.find('.connector');
      console.log(attachedConnectors);

      for (let i = 0; i < attachedConnectors.length; i++) {
        if (attachedConnectors[i].getAttr('from') === this.currentComponent.getAttr('id') || attachedConnectors[i].getAttr('to') === this.currentComponent.getAttr('id')){
          attachedConnectors[i].destroy();
        }
      }

      this.fromConnectors = this.fromConnectors.filter(n => n);
      this.toConnectors = this.toConnectors.filter(n => n);
    }

    this.tr.nodes([]);
    this.currentComponent.destroy();
    this.selectComponent = false;
    
    console.log("nnnnnnnnnnnnnnnnnnnnnnnnn");
    console.log(this.machines);
    console.log(this.fromConnectors);

    this.layer.batchDraw();
  }

  deleteAll(){
    this.layer.find('.machine').forEach((m) => m.remove());
    this.layer.find('.queue').forEach((q) => q.remove());
    this.layer.find('.connector').forEach((c) => c.remove());
    
    this.machines = [];
    this.queues = [];
    this.fromConnectors = [];
    this.toConnectors = [];
    
    this.machineCount = 1;
    this.queueCount = 1;
    this.currentAction = 'machine';

    this.queues.push(this.stackGroup.getAttr('id'));
    this.queues.push(this.loadGroup.getAttr('id'));

    this.layer.add(this.stackGroup);
    this.layer.add(this.loadGroup);
    this.layer.draw();
  }

  @HostListener('dblclick') onMousedblClick() {
    let pos = this.stage.getPointerPosition()!;
    let label = '';

    if (this.currentAction === 'machine') {
      let newMachine = this.machine.clone();
      newMachine.setAttr('x', pos.x);
      newMachine.setAttr('y', pos.y);

      let machineGroup = new Konva.Group({
        name: "machine",
        width: 56,
        height: 56
      });

      if (this.availableMachines.length > 0){
        label = 'M' + this.availableMachines[0].toString() + "\n" + "available";
        machineGroup.setAttr('number', this.availableMachines[0]);
        this.availableMachines.shift();
      } else {
        label = 'M' + this.machineCount.toString() + "\n" + "available";
        machineGroup.setAttr('number', this.machineCount);
        this.machineCount++;
      }

      machineGroup.add(newMachine);
      machineGroup.add(new Konva.Text({
        text: label,
        fontSize: 10,
        fontFamily: 'Calibri',
        fontStyle: 'bold',
        x : pos.x-18,
        y : pos.y-12,
        align: 'center'
      }));

      machineGroup.setAttr('id', 'M' + uuidv4().toString());
      machineGroup.setAttr('setX', pos.x);
      machineGroup.setAttr('setY', pos.y);

      this.machines.push(machineGroup.getAttr('id'));
      this.layer.add(machineGroup);
      this.layer.batchDraw();
    } else if (this.currentAction === 'queue') {
      let newQueue = this.queue.clone();
      newQueue.setAttr('x', pos.x - this.queue.getAttr('width')/2);
      newQueue.setAttr('y', pos.y - this.queue.getAttr('height')/2);

      let queueGroup = new Konva.Group({
        name: "queue",
        width: 48,
        height: 48
      });

      if (this.availableQueues.length > 0){
        label = 'Q' + this.availableQueues[0].toString() + "\n" + "products 0";
        queueGroup.setAttr('number', this.availableQueues[0]);
        this.availableQueues.shift();
      } else {
        label = 'Q' + this.queueCount.toString() + "\n" + "products 0";
        queueGroup.setAttr('number', this.queueCount);
        this.queueCount++;
      }

      queueGroup.add(newQueue);
      queueGroup.add(new Konva.Text({
        text: label,
        fontSize: 10,
        fontFamily: 'Calibri',
        fontStyle: 'bold',
        x : pos.x-22,
        y : pos.y-12,
        align: 'center'
      }));

      queueGroup.setAttr('id', 'Q' + uuidv4().toString());
      queueGroup.setAttr('setX', pos.x);
      queueGroup.setAttr('setY', pos.y);

      this.queues.push(queueGroup.getAttr('id'));
      this.layer.add(queueGroup);
      this.layer.batchDraw();
    } else {
      this.deleteComponent();
    }
  }

  simulate(){
    this.products = parseInt((<HTMLInputElement>document.getElementById("numberFill"))!.value);
    console.log((<HTMLInputElement>document.getElementById("numberFill"))!.value);
    this.apiService.startSimulation(this.products, this.machines, this.queues, this.fromConnectors, this.toConnectors).subscribe();
    this.poll();
  }

  reSimulate(){
    this.apiService.restartSimulation().subscribe();
    this.poll();
  }

  poll(){
    this.simulation = setInterval(() => this.apiService.polling().subscribe((data) => {
      let network = data;
      console.log(data);
      for (let i = 0; i < network.machines.length; i++) {
          let component = this.layer.findOne('#' + network.machines[i]);
          let componentShape = component.getAttr('children')[0];
          componentShape.setAttr('fill', network.machineStates[i]);
      }
      for (let i = 0; i < network.queues.length; i++) {
        let component = this.layer.findOne('#' + network.queues[i]);
        let componentText = component.getAttr('children')[1];
        let queueName = componentText.getAttr('text').split('\n')[0];
        componentText.setAttr('text', queueName + "\n" + "products " + network.queueProducts[i]);
      }
    }), 200);
  }

  pause(){
    this.apiService.pauseSimulation().subscribe();
    clearInterval(this.simulation);
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

  selectComponent2(){
    this.dataService.setAction('selection');
  }

  deleteComponent2(){
  }
}