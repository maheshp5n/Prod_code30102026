import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { NgxSpinner } from 'ngx-spinner/lib/ngx-spinner.enum';
import { GlobalserviceService } from '../../globalservice.service';
import { BulkutilService } from '../../services/bulkutil.service';
import { Page } from '../../services/model';

@Component({
  selector: 'app-bulkutils',
  templateUrl: './bulkutils.component.html',
  styleUrls: ['./bulkutils.component.scss']
})
export class BulkutilsComponent implements OnInit {

  pagedDate=new Page<any>();

  filters={
    page:0,
    size:20,
    orderby:'upload_name,desc'
  }
  bsLocation=`${window.location.protocol}//${window.location.host}/`
  constructor(private _bulkutilService:BulkutilService,
    private http:HttpClient,private _global:GlobalserviceService,private activated:ActivatedRoute,private ngx:NgxSpinnerService) {
      this.activated.queryParams.subscribe(data=>{
        if(data['page']!=undefined&&data['page']!=null&&data['page']!=''){
          this.filters.page=Number(data['page']);
        }
        if(data['size']!=undefined&&data['size']!=null&&data['size']!=''){
          this.filters.size=Number(data['size']);
        }
        this.getOptionData();
      });
     }

  ngOnInit() {

  }

  getOptionData(){
    this._bulkutilService.getBulkUtils(this.filters).subscribe(data=>this.pagedDate=data);
  }

  uploadFiles($event,id){
    if(confirm('Are you sure ?')){
      const file = $event.target.files[0];
      const frm=new FormData();
      frm.append('file',file,file.name);
      this.ngx.show();
      this.http.post(this.getUrlLink(id),frm).subscribe(data=>{
        $event.value='';
        this.ngx.hide();
      },error=>{
        $event.value='';
        this.ngx.hide();
        this._global.alert({type:'alert',message:error.error.message});
      });
    }
    
  }

  getUrlLink(id):any{
    for(let i=0;i<this.pagedDate.content.length;i++){
      if(this.pagedDate.content[i].id==id){
        return this.pagedDate.content[i].upload_api;
      }
    }
  }



  downloadLink(d){
    //href="{{bsLocation}}{{d.template_api}}"
    window.open(`${this.bsLocation}${d.template_api}`,`${d.template_api_name}_${d.id}`,'menubar=0,resizable=0,scrollbars=0,titlebar=0,toolbar=0,top=0,left=0');
  }

}
