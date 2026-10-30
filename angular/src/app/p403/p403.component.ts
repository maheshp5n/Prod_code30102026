import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-p403',
  templateUrl: './p403.component.html',
  styleUrls: ['./p403.component.scss']
})
export class P403Component implements OnInit {

  msgTemplate="";

  msg="";
  counter=0;
  constructor() { }

  ngOnInit() {
    this.startAnimation();
  }
  startAnimation(){
    this.msg="";
    let that=this;
    setTimeout(function() {
      var se = setInterval(function() {
          that.counter++;
          that.msg = that.msgTemplate.slice(0, that.counter) + "|";
          if (that.counter == that.msgTemplate.length) {
              clearInterval(se);
              that.msg=that.msgTemplate;
          }
      }, 10);
  },0);
  
  }
}
