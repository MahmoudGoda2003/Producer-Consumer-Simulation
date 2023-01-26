import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { ProductConsumerComponent } from './product-consumer/product-consumer.component';

@NgModule({
  declarations: [
    AppComponent,
    ProductConsumerComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
