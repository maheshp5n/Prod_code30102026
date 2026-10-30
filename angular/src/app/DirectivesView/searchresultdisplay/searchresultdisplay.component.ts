import { Component, Input, OnInit } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { SearchService } from '../../services/search.service';

@Component({
  selector: 'app-searchresultdisplay',
  templateUrl: './searchresultdisplay.component.html',
  styleUrls: ['./searchresultdisplay.component.scss']
})
export class SearchresultdisplayComponent implements OnInit {

  result;
  tblNames=[];

  @Input()
   set searchKey(data){
    if(data){
      this.getSearchResult(data);
    }
  }
  nodata:boolean=false;

  constructor(private _search:SearchService,private _spinner:NgxSpinnerService) { }

  ngOnInit() {
  }
  getSearchResult(data){
    this._spinner.show();
    this._search.searchAdvanceData(data).subscribe(sdata=>{
      this.nodata=false;
      try{
        this.result=sdata;
        this.tblNames= Object.keys(this.result);
  
      }catch(e){

      }
   
            this._spinner.hide();
    },error=>this._spinner.hide());
  }
  getObjKey(data){
    return Object.keys(data);
  }
}
