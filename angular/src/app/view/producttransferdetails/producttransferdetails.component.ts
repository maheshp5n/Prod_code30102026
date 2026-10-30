import { Component, OnInit, ViewChild } from '@angular/core';
import { LocationProductTransferItemResponse, PTResponse } from '../../services/model';
import { ProductTransferServices } from '../../services/producttransfer.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { PrudcttransferDataService } from './producttransferdata.service';
import { GlobalserviceService } from '../../globalservice.service';
import { ModalDirective } from 'ngx-bootstrap';
import { PdfServiceService } from '../../services/pdf-service.service';

@Component({
  selector: 'app-producttransferdetails',
  templateUrl: './producttransferdetails.component.html',
  styleUrls: ['./producttransferdetails.component.scss']
})
export class ProducttransferdetailsComponent implements OnInit {
  @ViewChild('confirmationPopup',{static:false}) confirmationPopup:ModalDirective
  transferIItemId:number;
  productTransferDetails:PTResponse=new PTResponse();
  constructor(private _producttransferService:ProductTransferServices
    ,private _ngxspinnnerService:NgxSpinnerService,private global:GlobalserviceService,
    private _activatedRoute:ActivatedRoute,private pdfservice:PdfServiceService,
    private _dataservice:PrudcttransferDataService) {
      this._activatedRoute.params.subscribe(param=>{
        this.transferIItemId=param['transferitemid'];
        this.getItemDetails(this.transferIItemId);
        this._dataservice.dataServiceLocationTResponse=new LocationProductTransferItemResponse();
        this._dataservice.dataServiceLocationTResponse.locationProductTransferItemId=this.transferIItemId;
      });
     }
  ngOnInit() {
  }
  getItemDetails(id:number){
    this._ngxspinnnerService.show();
    this._producttransferService.getTransferDetailsById(id).subscribe(data=>{
      this._ngxspinnnerService.hide();
      this.productTransferDetails=data;
      

      this._dataservice.dataServiceLocationTResponse=data;
    },error=>{
      this._ngxspinnnerService.hide();
      this.global.alert({ type: 'alert', message:error.error.message });
    });
  }
  createPDF() {
    this._ngxspinnnerService.show();
    this.pdfservice.createPDF('api/producttransfer', 'productTransferId',
    this.transferIItemId).subscribe(data => {
        const file = new Blob([data], { type: 'application/pdf' });

        if (window.navigator && window.navigator.msSaveOrOpenBlob) {
          window.navigator.msSaveOrOpenBlob(file);
        } else {
          const fileURL = URL.createObjectURL(file);
          window.open(fileURL);
        }
        // this.getFileList('Quote', this.expensesmanagement.expenseManagementId);
        this._ngxspinnnerService.hide();
        this.confirmationPopup.hide();
        this.global.alert({ type: '', message: "Created successfully"});
      }, err => {
        this._ngxspinnnerService.hide();
        this.confirmationPopup.hide();
        this.global.alert({ type: 'alert', message: err.error.message });
      });
  }
}
