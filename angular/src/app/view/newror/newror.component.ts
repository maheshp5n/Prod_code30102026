import { GlobalserviceService } from '../../globalservice.service';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-newror',
  templateUrl: './newror.component.html',
  styleUrls: ['./newror.component.scss']
})
export class NewrorComponent implements OnInit {

  passingdata;
  show = false;
  constructor(private _global: GlobalserviceService, private _activated: ActivatedRoute, private _router: Router) {
    this._activated.queryParams.subscribe(param => {
      if (param != undefined) {
        this.passingdata = param;
        console.log(this.passingdata);
        this.show = true;
      }
    });
  }

  ngOnInit() {
  }

  onCancel() {
    this._global.alert({ type: 'alert', message: 'Last action canceled' });
    this._router.navigate(['/productreturnorder', this.passingdata.returnOrderId]);
  }
  onsaveData() {
    this._global.alert({ type: '', message: `Updated successfully` });
    this._router.navigate(['/productreturnorder', this.passingdata.returnOrderId]);
  }

}
