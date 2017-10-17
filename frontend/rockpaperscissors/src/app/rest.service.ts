import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import 'rxjs/add/operator/map'

export const path = "http://localhost:8080";

@Injectable()
export class RestService {
  data: any;
  game: any;

  constructor(private http: Http) {
    this.data = {};
    this.game = {};
    this.refreshData();
  }

  getGameTypes() {
    console.debug("Requesting game types");
    return this.http.get(`${ path }/types`).map((r: Response) => r.json());
  }

  getGameModes() {
    console.debug("Requesting game modes");
    return this.http.get(`${ path }/modes`).map((r: Response) => r.json());
  }

  refreshData() {
    this.getGameModes().subscribe(data => {
      console.debug("games modes received: " + data);
      this.data.modes = data;
    });
    this.getGameTypes().subscribe(data => {
      console.debug("games types received: " + data);
      this.data.types = data;
    });
  }

  public startNewGame(gameType: String, gameMode: String) {
    console.debug("starting new game: " + gameType + ", " + gameMode);
    this.game = {stat: [0, 0, 0], stage: 0};
    this.http.post(`${ path }/new/${ gameType }/${ gameMode}`, "")
      .map((r: Response) => r.text())
      .subscribe(data => {
        console.debug("new game started: " + data);
        this.game.id = data;
        this.loadGameInfo();
      });
  }

  public charge(move: String) {
    this.game.first = move;
    this.game.second = "";
    this.game.result = "";
    this.game.error = "";

    console.debug("charge: " + move);
    this.http.get(`${ path }/generate/${ this.game.id }`)
      .map((r: Response) => r.text())
      .subscribe(data => {
        console.warn("Computer move: " + data);
        this.game.second = data;
        this.calculate();
      }, error => {
        console.error(error.json());
        this.game.error = error.json().message || error.json().error;
      });
  }

  public calculate() {
    console.debug("calculate");
    this.http.get(`${ path }/calculate/${ this.game.id }/${ this.game.first }/${ this.game.second }`)
      .map((r: Response) => r.text())
      .subscribe(data => {
        console.warn("Move result: " + data);
        this.game.result = data;
        this.statistic();
      });
  }

  public statistic() {
    console.debug("statistic");
    this.http.get(`${ path }/stat/${ this.game.id }`)
      .map((r: Response) => r.json())
      .subscribe(data => {
        console.warn("State: " + data);
        this.game.stat = data;
      });
  }

  private loadGameInfo() {
    console.debug("loading game info: " + this.game.id);
    this.http.get(`${ path }/moves/${ this.game.id }`)
      .map((r: Response) => r.json())
      .subscribe(data => {
        console.debug("available game moves: " + data);
        this.game.moves = data;
      });
    this.http.get(`${ path }/info/${ this.game.id }`)
      .map((r: Response) => r.text())
      .subscribe(data => {
        console.debug("game name: " + data);
        this.game.name = data;
      });
    this.http.get(`${ path }/isgeneratedmove/${ this.game.id }`)
      .map((r: Response) => r.text())
      .subscribe(data => {
        console.debug("can generate moves: " + data);
        this.game.isgeneratedmove = data === "true";
      });
    this.http.get(`${ path }/winning/${ this.game.id }`)
      .map((r: Response) => r.json())
      .subscribe(data => {
        console.debug("winning combinations: ", data);
        this.game.winning = data.map(o => [Object.keys(o)[0], o[Object.keys(o)[0]]]);
      });
  }

}
