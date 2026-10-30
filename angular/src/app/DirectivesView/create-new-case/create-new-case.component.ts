import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';

@Component({
  selector: 'app-create-new-case',
  templateUrl: './create-new-case.component.html',
  styleUrls: ['./create-new-case.component.css']
})
export class CreateNewCaseComponent implements OnInit {
  @ViewChild('createNewCaseModel',{static:false}) createNewCaseModel:ModalDirective;
  

  constructor() { }

  ngOnInit() {
  }

}
