import { Component, OnInit } from '@angular/core';
import { PanIndiaProductSummary, Page } from '../../../services/model';
import { ProductsService } from '../../../services/products.services';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-stocksummary',
  templateUrl: './stocksummary.component.html',
  styleUrls: ['./stocksummary.component.scss']
})
export class StocksummaryComponent implements OnInit {
  summary:Page<PanIndiaProductSummary>=new Page<PanIndiaProductSummary>();
  constructor(private _productService:ProductsService,private _ngx:NgxSpinnerService) { }
  responsive:boolean=true;
  ngOnInit() {
    this.getProductSummary();
  }
  getProductSummary(filter?){
    this._ngx.show();
    this._productService.getPanIndiaProductList(filter).subscribe(data=>{
      try{
        this.summary=data;
      }catch(e){}
      this._ngx.hide();
    },error=>this._ngx.hide());
  }
  pageChanged($event){
    this.getProductSummary(`page=${($event.page-1)}`);
  }
}
