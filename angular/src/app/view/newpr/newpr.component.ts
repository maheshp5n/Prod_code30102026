import { GlobalserviceService } from '../../globalservice.service';
import { LPRequest } from '../../services/model';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-newpr',
  templateUrl: './newpr.component.html',
  styleUrls: ['./newpr.component.scss']
})
export class NewprComponent implements OnInit {

  locationResponse;
  show = false;
  constructor(private _activatedRoute: ActivatedRoute, private _global: GlobalserviceService, private _router: Router) {
    _activatedRoute.queryParams.subscribe(param => {
      if (param != undefined) {
        this.locationResponse = param;
        this.show = true;
      }  
    });
  }

  ngOnInit() {
  }
  onCancel() {
    this._global.alert({ type: 'alert', message: 'Last action canceled' });
    this._router.navigate(['/location', this.locationResponse.locationId]);
  }
  onSave($event: LPRequest) {
    this._global.alert({ type: '', message: `Created Successfully` });
    this._router.navigate(['/productrequest', $event.id]);
  }
}
