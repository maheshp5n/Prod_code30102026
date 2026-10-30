import { Component, Input, OnInit } from '@angular/core';
import { ServiceOrderService } from '../../../../services/serviceorder.servvices';

@Component({
  selector: 'app-bat-serviceorderdetails-view',
  templateUrl: './bat-serviceorderdetails-view.component.html',
  styleUrls: ['./bat-serviceorderdetails-view.component.scss']
})
export class BatServiceorderdetailsViewComponent implements OnInit {

  @Input()
  serviceOrderDetails: any;

  displaybutton = {
    info: true,
    sys: true,
    shippingInfo:true
  };
  openModal = {
    assignto: false
  };


  constructor(private _serviceOrderService: ServiceOrderService) {

  }

  ngOnInit() {

  }

}
