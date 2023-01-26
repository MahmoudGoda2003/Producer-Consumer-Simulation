import { Component, OnInit } from '@angular/core';
import {KonvaModule} from "ng2-konva";
import {KonvaComponent} from "ng2-konva";
import { Konva } from "konva/cmj/_FullInternals";
import { TitleStrategy } from '@angular/router';

export class factoryShape{

  public shapecreator(s:string  , id : string)
  {
    if(s == 'M')
      return new circle( id);
    else if ( s == 'Q')
      return new rectangle(id);
    else
    return null
  }
}


export interface Shape {
  structure :any;
  get(): any;
}


class circle implements Shape {

  structure;

  constructor(id : string) {

    this.structure = new Konva.Circle({
      x: 200,
      y: 200,
      scaleX:1,
      scaleY:1,
      radius: Math.abs(50),
      stroke:"yellow",
      strokeWidth: 2,
      draggable: false,
      id: id,
      alpha : 20,
      fill:"#FFFFFF",

    });
  }

  public get(): any {
    return this.structure;

  }
}


class rectangle implements Shape {

  structure;

  constructor(id : string) {

    this.structure = new Konva.Rect({ 
      x: 100,
      y: 100,
      scaleX:1,
      scaleY:1,
      width:100,
      height : 70,
      stroke:"red",
      strokeWidth: 2,
      draggable: false,
      alpha : 20,
      id: id,
      fill:"#FFFFFF",

    });
  }

  public get(): any {
    return this.structure;

  }
}


