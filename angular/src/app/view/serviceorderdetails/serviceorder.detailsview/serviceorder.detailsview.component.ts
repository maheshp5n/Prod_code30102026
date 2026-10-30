import { ServiceOrderService } from '../../../services/serviceorder.servvices';
import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-serviceorderdetailsview',
  templateUrl: './serviceorder.detailsview.component.html',
  styleUrls: ['./serviceorder.detailsview.component.scss']
})
export class ServiceorderDetailsviewComponent implements OnInit {

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
