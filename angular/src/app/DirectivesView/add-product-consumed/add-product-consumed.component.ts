import { Component, OnInit, ViewChild, Output, EventEmitter} from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { AccountCreate } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-add-product-consumed',
  templateUrl: './add-product-consumed.component.html',
  styleUrls: ['./add-product-consumed.component.scss']
})
export class AddProductConsumedComponent implements OnInit {
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;

  @Output()
  onAccountCreated: EventEmitter<AccountCreate> = new EventEmitter<AccountCreate>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  private _isEditMode: boolean;
  message: string;
  istrue: boolean;
  createAccount: AccountCreate = new AccountCreate();  

  
  constructor(private spinner:NgxSpinnerService) { }

  ngOnInit() {
  }

  cancelData() {
    this.createAccount = new AccountCreate();
    this.onCancle.emit('cancled');
  }
  
  postCreateAccount(){

  }

  openPopUp() {
    if (this.istrue == true) {
      this.message = "Submitted Successfully";
      setTimeout(() => {
        this.spinner.hide();
        this.popupSuccessModel.show();

        setTimeout(() => {
          this.cancelData()
          this.popupSuccessModel.hide()
        }, 2000
        );


      }, 1000
      );



    } else {
      this.message = "Submittion Failed";
      this.spinner.show();
      setTimeout(() => {
        this.spinner.hide();
        this.popupSuccessModel.show();

        setTimeout(() => {
          this.popupSuccessModel.hide()
        }, 2000
        );


      }, 1000
      );

    }

  }
  popcheck() {
    setTimeout(() => {
      this.spinner.hide();
      this.popupSuccessModel.show();

      setTimeout(() => {
        this.popupSuccessModel.hide()
      }, 2000
      );


    }, 1000
    );

  }


}
