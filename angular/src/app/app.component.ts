import { ModalDirective } from 'ngx-bootstrap';
import { GlobalserviceService } from './globalservice.service';
import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { SwUpdate } from '@angular/service-worker';


@Component({
  selector: 'app-component',
  templateUrl: './app.component.html',
  styleUrls:['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {

  alertMeta = {
    type: 'alert-danger',
    message: ''
  };

  info={
    type:'',
    message:'I look for guest-written Extra Crunch stories that will help other entrepreneurs be more successful, which is why I routinely turn down submissions that seem overly promotional.',
    show:false,
  }

  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;
  constructor(private router: Router, private _global: GlobalserviceService,
    private swUpdate:SwUpdate
    ) {
    this.swUpdate.available.subscribe(data=>{
      this.swUpdate.activateUpdate().then(()=>document.location.reload());
    });
  }

  inter;
  gnmessage;
  ngOnInit() {
    this.inter = this._global.onMessage().subscribe(data => {
      this.alertMeta.type = data.type == 'alert' ? 'alert alert-danger' : 'alert alert-success';
      this.alertMeta.message = data.message;
      this.popupSuccessModel.show();
    });
    this.gnmessage=this._global.onMessageForUser().subscribe(data=>{
      this.info.type=data.type;
      this.info.message=data.message;
      this.info.show=true;
      console.log(data);
    });

    this.router.events.subscribe((evt) => {
      if (!(evt instanceof NavigationEnd)) {
        return;
      }
      window.scrollTo(0, 0);
    });

  }
  ngOnDestroy() {
    this.inter.unsubscribe();
    this.gnmessage.unsubscribe();
  }
}
