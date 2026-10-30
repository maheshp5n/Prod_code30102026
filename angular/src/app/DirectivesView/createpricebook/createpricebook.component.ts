import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { PriceBookResponse, PriceBookRequest } from '../../services/model';
import { ModalDirective } from 'ngx-bootstrap';
import { PriceBookService } from '../../services/pricebook.service';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-createpricebook',
  templateUrl: './createpricebook.component.html',
  styleUrls: ['./createpricebook.component.scss']
})
export class CreatepricebookComponent implements OnInit {
  priceResp: PriceBookResponse = new PriceBookResponse();
  request: PriceBookRequest = new PriceBookRequest();
  isVisible: boolean = false;
  _isEditMode: boolean;
  istrue: boolean;
  message: string;
  nameTamp:string;
  IdTamp:number;
  popupNgClass: string = 'alert alert-danger center center-block';
  @ViewChild('popupSuccessModel',{static:false}) popupSuccessModel: ModalDirective;
  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
    console.log(this._isEditMode);
    
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }


  @Input()
  set editPriceBookData(priceResp: PriceBookResponse) {
    this.priceResp = priceResp;
  }

  get editPriceBookData(): PriceBookResponse {
    return this.priceResp;
  }

  @Output()
  onpricebookCreated: EventEmitter<PriceBookRequest> = new EventEmitter<PriceBookRequest>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
 constructor(private pricebookservice: PriceBookService, private spinner: NgxSpinnerService) { }


  ngOnInit() {
    // this.isEditMode=false;
    if(!this.isEditMode) {
      this.priceResp=new PriceBookResponse();
    }
  }
  PricebookNameTamp:string;
  postPricebook(){
    if(this.priceResp.name===undefined || this.priceResp.name===null){
      this.message="Please Fill Price Book Name";
      this.popupNgClass = 'alert alert-danger center center-block';
      this.popupSuccessModel.show();
     }
     else if(this.priceResp.name!==undefined && (this.priceResp.name).length>200){
     this.message="Price Book Name Exceeds 200 Characters"
     this.popupNgClass = 'alert alert-danger center center-block';
     this.popupSuccessModel.show()
     }
     else if( (this.priceResp.description!==null && this.priceResp.description!==undefined) && (this.priceResp.description).length>500 ){
     this.message="Price Book Name Exceeds 200 haracter"
       this.message='Please Fill Description Under 500 Characters';
       this.popupNgClass = 'alert alert-danger center center-block';
       this.popupSuccessModel.show()
      }
    else{
      if(!this.isEditMode){
        this.request.name=this.priceResp.name;
        this.request.active=this.priceResp.active;
        this.request.description=this.priceResp.description;
        this.request.standardPriceBook=this.priceResp.standardPriceBook;
        this.spinner.show();
        this.pricebookservice.savePriceBook(this.request).subscribe(data=>{
          this.IdTamp=data.id
          this.spinner.hide();
          this.PricebookNameTamp=data.name
          this.onpricebookCreated.emit(data);
          this.message = 'Created Successfully';
          this.popupNgClass = 'alert alert-success center center-block';
          
          this.popupSuccessModel.show();
        },  (error) => {
          this.spinner.hide();
          this.message = error.error.message;
          this.popupSuccessModel.show();
      })
      
      }
      else{
        this.request.id=this.priceResp.id;
        this.request.name=this.priceResp.name;
        this.request.active=this.priceResp.active;
        this.request.description=this.priceResp.description;
        this.request.standardPriceBook=this.priceResp.standardPriceBook;
        this.spinner.show();
        this.pricebookservice.updatePicebook(this.request).subscribe(data=>{
          this.spinner.hide();
          this.IdTamp=data.id
          this.PricebookNameTamp=data.name;
          this.onpricebookCreated.emit(data);
          this.message = 'Updated Successfully'
          this.popupNgClass = 'alert alert-success center center-block';
          this.spinner.hide();
          this.popupSuccessModel.show();
        },error=>{
          this.spinner.hide();
          this.message=error.error.message;
          this.popupSuccessModel.show();
         
        })
      
      }

    }

    
  }
  cancelPricebookData(){
    this.onCancle.emit('cancled');
  }
  popupClose() {
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancle.emit('cancled');
    }
  }

}
