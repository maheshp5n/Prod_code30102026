import { GlobalserviceService } from '../../globalservice.service';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-newptr',
  templateUrl: './newptr.component.html',
  styleUrls: ['./newptr.component.scss']
})
export class NewptrComponent implements OnInit {

  locationProductRequestResponse;
  show = false;
  constructor(private _global: GlobalserviceService, private _activatedRoute: ActivatedRoute, private _route: Router) {
    this._activatedRoute.queryParams.subscribe(data => {
      if (data != undefined) {
        this.locationProductRequestResponse = data;
        this.show=true;
      }

    });
  }

  ngOnInit() {
  }
  onItemReceivedCancled($event) {
    this._global.alert({ type: 'alert', message: 'Last action canceled' });
    this._route.navigate(['/productrequest', this.locationProductRequestResponse.prId]);
  }
  onItemReceived($event) {
    this._global.alert({ type: '', message: 'Updated Successfully' });
    this._route.navigate(['/productrequest', this.locationProductRequestResponse.prId]);
  }
}
