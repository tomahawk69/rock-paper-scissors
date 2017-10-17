import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';

@Component({
  selector: 'game-selector',
  templateUrl: './game-selector.component.html',
  styleUrls: ['./game-selector.component.css']
})
export class GameSelectorComponent implements OnInit {
  public notSelected = "NONE";
  public selectedMode: String;
  public selectedType: String;

  constructor(private restService: RestService) {
    this.selectedMode = this.notSelected;
    this.selectedType = this.notSelected;
  }

  ngOnInit() {

  }

  public getModes() {
    return this.restService.data.modes;
  }

  public getTypes() {
    return this.restService.data.types;
  }

  public startNewGame() {
    this.restService.startNewGame(this.selectedType, this.selectedMode);
  }

}
