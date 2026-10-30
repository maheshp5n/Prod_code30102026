import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { BulkutilService } from '../../services/bulkutil.service';
import { Page } from '../../services/model';

@Component({
  selector: 'app-bulkutilsdetails',
  templateUrl: './bulkutilsdetails.component.html',
  styleUrls: ['./bulkutilsdetails.component.scss']
})
export class BulkutilsdetailsComponent implements OnInit {

  bulkOptions;
  filter={
    page:0,
    size:20,
    orderby:'last_mofidied_date,desc'
  }
  _id;

  actions=new Page<any>();
  
  bslocation=`${window.location.protocol}//${window.location.host}`

  constructor(private _ngx:NgxSpinnerService,private _bulkservice:BulkutilService,private _activateRoute:ActivatedRoute) {
    this._activateRoute.params.subscribe(param=>{
      if(param['id']!=undefined&&param['id']!=null){
        this._id=Number(param['id']);
        this.getBulkOptions(this._id);
      }
    });
    this._activateRoute.queryParams.subscribe(data=>{
      if(data['page']!=undefined&&data['page']!=null&&data['page']!=''){
        this.filter.page=Number(data['page']);
      }
      if(data['size']!=undefined&&data['size']!=null&&data['size']!=''){
        this.filter.page=Number(data['size']);
      }
    });

   }

  ngOnInit() {
  }

  getBulkOptions(id){
    this._ngx.show();
    this._bulkservice.getBulkUtilById(id).subscribe(data=>{
      this._ngx.hide();
      this.bulkOptions=data;
      this.getBulkAction(this._id);
    },error=>{
      this._ngx.hide();

    });
  }

  getBulkAction(id){
    this._ngx.show();
    this._bulkservice.getBulkAction(id,this.filter).subscribe(data=>{
      this._ngx.hide();
      this.actions=data;
    },error=>{
      this._ngx.hide();
    });
  }

}
