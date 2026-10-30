import { GlobalserviceService } from '../../globalservice.service';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-newpt',
  templateUrl: './newpt.component.html',
  styleUrls: ['./newpt.component.scss']
})
export class NewptComponent implements OnInit {
  locationProductRequestResponse;
  show = false;
  constructor(private _global: GlobalserviceService, private _activeRoutes: ActivatedRoute, private _router: Router) {
    _activeRoutes.queryParams.subscribe(param => {
      if (param != undefined) {
        this.locationProductRequestResponse = param;
        this.show = true;
      }
    });
  }

  ngOnInit() {
  }

  onCancel() {
    this._global.alert({ type: 'alert', message: 'Last action canceled' });
    this._router.navigate(['/productrequest', this.locationProductRequestResponse.prId]);
  }
  onSave($event) {
    this._global.alert({ type: '', message: `Updated successfully` });
    this._router.navigate(['/productrequest', this.locationProductRequestResponse.prId]);
  }
}
