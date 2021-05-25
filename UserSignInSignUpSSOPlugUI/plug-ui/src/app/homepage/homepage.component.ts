import { Component, OnInit } from '@angular/core';
import { HomepageService } from '../services/homepage.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {

  helloWorldMsg: string;
  restrictedMsg: string;

  constructor(
    private homepageService: HomepageService
  ) {}

  ngOnInit(): void {
  }

  helloWorld() {
    this.homepageService
      .helloWorld()
      .subscribe(data => {
        this.helloWorldMsg = data;
      })
  }

  restricted() {
    this.homepageService
      .restricted()
      .subscribe(data => {
        this.helloWorldMsg = data;
      })
  }

}
