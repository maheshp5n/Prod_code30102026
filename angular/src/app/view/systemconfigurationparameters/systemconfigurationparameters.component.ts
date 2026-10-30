import { Component, OnInit, ViewChild, Output, EventEmitter, OnDestroy } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { AssetsResponse } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { AssetsService } from '../../services/assets.services';


@Component({
  selector: 'app-systemconfigurationparameters',
  templateUrl: './systemconfigurationparameters.component.html',
  styleUrls: ['./systemconfigurationparameters.component.scss']
})
export class SystemconfigurationparametersComponent implements OnInit,OnDestroy {
  
  constructor(private spinner: NgxSpinnerService, private _assetsService: AssetsService) { }
  currentdate:Date=new Date();
  intveral;
  ngOnInit() {
    this.intveral= setInterval(()=>this.currentdate=new Date(),1000);
  }
  ngOnDestroy(){
    clearInterval(this.intveral)
  }
}
