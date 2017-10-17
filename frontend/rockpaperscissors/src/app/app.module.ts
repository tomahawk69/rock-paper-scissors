import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppComponent } from './app.component';
import { GameSelectorComponent } from './game-selector/game-selector.component';
import { RestService } from './rest.service';
import { GameZoneComponent } from './game-zone/game-zone.component';

@NgModule({
  declarations: [
    AppComponent,
    GameSelectorComponent,
    GameZoneComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    NgbModule.forRoot()
  ],
  providers: [RestService],
  bootstrap: [AppComponent]
})
export class AppModule { }
