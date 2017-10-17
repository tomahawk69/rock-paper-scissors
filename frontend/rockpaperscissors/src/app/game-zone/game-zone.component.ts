import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { RestService } from '../rest.service';

@Component({
  selector: 'game-zone',
  templateUrl: './game-zone.component.html',
  styleUrls: ['./game-zone.component.css']
})
export class GameZoneComponent implements OnInit {
  public selectedMove: String;

  constructor(private restService: RestService, private cdr: ChangeDetectorRef) {
    this.selectedMove = "";
  }

  ngOnInit() {
  }

  public getGameInfo() {
    return this.restService.game;
  }

  public isGameStarted(): Boolean {
    return this.restService.game.id;
  }

  public getGameError(): String {
    return this.restService.game.error;
  }

  public isGameError(): Boolean {
    return this.restService.game.error;
  }

  public getMoves() {
    return this.restService.game.moves;
  }

  public getFirst(): String {
    if (this.restService.game.stage == 1) {
      return "*******";
    } else {
      return this.restService.game.first;
    }
  }

  public getSecond(): String {
    return this.restService.game.second;
  }

  public getResult(): String {
    return this.restService.game.result;
  }

  public getStat(): any {
    return this.restService.game.stat;
  }

  public charge() {
    if (this.isGeneratedMove()) {
      this.restService.charge(this.selectedMove);
    } else if (this.restService.game.stage == 1) {
      this.restService.game.second = this.selectedMove;
      this.restService.calculate();
      this.restService.game.stage = 0;
    } else {
      this.restService.game.first = this.selectedMove;
      this.restService.game.second = "";
      this.restService.game.result = "";
      this.restService.game.stage = 1;
    }
    setTimeout(() => {this.selectedMove = "";}, 10);
  }

  public isGeneratedMove(): Boolean {
    return this.restService.game.isgeneratedmove;
  }

  public getChargeTitle() {
    // todo move to view
    if (this.isGeneratedMove()) {
      return "You turn";
    } else if (this.restService.game.stage == 1) {
      return "Second person turn";
    } else {
      return "First person turn";
    }
  }

   public onChange(move) {
    console.debug(move);
    this.selectedMove = move;
   }
}
