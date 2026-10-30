import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../globalservice.service';
import { LocationService } from '../../services/location.service';
import { Loginservices } from '../../services/login.services';
import { ProductsForList } from '../../services/model';
import { ProductsService } from '../../services/products.services';
import { UserService } from '../../services/user.services';

@Component({
  selector: 'app-myinventorychange',
  templateUrl: './myinventorychange.component.html',
  styleUrls: ['./myinventorychange.component.scss']
})
export class MyinventorychangeComponent implements OnInit {

  _reqdata;

  searchREsult:Array<ProductsForList>=[];

  changeCode={
    part_code:'',
    serial_number:'',
    state_id:0,
    remark:''
  }

  @Input()
  set reqData(d){
    if(d!=undefined&&d!=null){
      this._reqdata=d;
      this.changeCode.part_code=d.partCode;
      this.changeCode.serial_number=d.serialNumber;
      this.changeCode.state_id=Number(d.serialNumberStateId);
  
      const prd=new ProductsForList();
      prd.productPartCode=d.partCode;
      prd.productDescription=d.productDescription;
      prd.bothvalues=d.partCode+' ('+d.productDescription+')';
      this.searchREsult.push(prd);
    }
    
  }

  get reqData(){
    return this._reqdata;
  }

  @Output()
  onClose:EventEmitter<string>=new EventEmitter();

  @Output()
  onSave:EventEmitter<string>=new EventEmitter();

  currentUserDetails;

  constructor(private _productservice:ProductsService,private locationservice:LocationService,private _global:GlobalserviceService,private ngx:NgxSpinnerService) {
    this.currentUserDetails=JSON.parse(localStorage.getItem('login'));
   }



  ngOnInit() {

  }

  searchPartcode($event){
    this._productservice.getSmallForlist($event.term).subscribe(data=>this.searchREsult=data);
  }
  getStatus(id):string{
    if(id===1){
      return 'OK';
    }
    if(id===2){
      return 'Faulty'
    }
    if(id===3){
      return 'Scrap';
    }
    if(id===4){
      return 'Rejected'
    }
    if(id===5){
      return 'BER';
    }
    if(id===6){
      return 'DOA'
    }
  }

  getDescription():string{
    const lt=this.searchREsult.length;
    for(let i=0;i<lt;i++){
      if(this.searchREsult[i].productPartCode){
        return this.searchREsult[i].productDescription;
      }
    }
  }

  saveData(){
    if(this.changeCode.remark===undefined||this.changeCode.remark===null||this.changeCode.remark===''){
      this._global.alert({type:'alert',message:'Please insert remark'});
      return;
    }
    this.ngx.show();
    this.locationservice.changePartcodeAndState(this._reqdata.lineItemId,this.changeCode).subscribe(data=>{
      this.ngx.hide();
      this.onSave.emit('save');
    },error=>{
      this.ngx.hide();
      this._global.alert({type:'alert',message:error.error.message});
    });
  }

  cancel(){
    this.onClose.emit('');
  }
}
