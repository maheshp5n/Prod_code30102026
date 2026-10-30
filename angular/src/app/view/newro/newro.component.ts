import { GlobalserviceService } from '../../globalservice.service';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-newro',
  templateUrl: './newro.component.html',
  styleUrls: ['./newro.component.scss']
})
export class NewroComponent implements OnInit {

  locationResponse;
  show = false;
  constructor(private _global: GlobalserviceService, private _activeRoutes: ActivatedRoute, private _router: Router) {
    this._activeRoutes.queryParams.subscribe(param => {
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
  onSave($event) {
    this._global.alert({ type: '', message: `Return order <a href="#/productreturnorder/${$event.lprOrderid}">${$event.lprOrderidManual} Created Successfully </a> ` });
    this._router.navigate(['/productreturnorder', $event.lprOrderid]);
  }

}
