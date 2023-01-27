import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { FactoryComponent } from './Components/factory/factory.component';
import { ToolbarComponent } from './Components/toolbar/toolbar.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';

@NgModule({
  declarations: [
    AppComponent,
    FactoryComponent,
    ToolbarComponent
  ],
  imports: [
    BrowserModule,
    NoopAnimationsModule,
    MatButtonToggleModule,
    MatIconModule,
    MatGridListModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
