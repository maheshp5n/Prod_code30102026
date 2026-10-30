import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GlobalserviceService } from '../../globalservice.service';

@Component({
  selector: 'app-newserviceorder',
  templateUrl: './newserviceorder.component.html',
  styleUrls: ['./newserviceorder.component.css']
})
export class NewserviceorderComponent implements OnInit {

  constructor( private _global: GlobalserviceService, private _router: Router) { }

  ngOnInit(): void {
  }
  serviceOrderCancel() {
    this._global.alert({ type: 'alert', message: 'Last action canceled' });
    this._router.navigate(['/serviceorder']);
  }
  onSave($event) {
    this._global.alert({ type: '', message: `Created Successfully` });
    this._router.navigate(['/serviceorder', $event.id]);
  }
}
