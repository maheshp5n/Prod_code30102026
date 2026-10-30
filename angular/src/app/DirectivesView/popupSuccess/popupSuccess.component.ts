import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';

@Component({
  selector: 'app-popupSuccess',
  templateUrl: './popupSuccess.component.html',
  styleUrls: ['./popupSuccess.component.css']
})
export class PopupSuccessComponent implements OnInit {
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  _message: string;


  constructor() { }

  ngOnInit() {

  }


  @Input()
  set message(message: string) {
    this._message = message;
  }

  get message(): string {
    return this._message;
  }

}
