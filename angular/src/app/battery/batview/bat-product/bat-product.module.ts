import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule, BsDropdownModule } from 'ngx-bootstrap';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { FieldValidation } from '../../../services/validation.services';
// import { SearchfiltPipe } from '../../../view/accounts/searchfilt.pipe';
import { ProductsService } from '../../../services/products.services';
import { BatProductComponent } from './bat-product.component';
import { BatProductRouting } from './bat-product.routing';
import { BtCreateProductModule} from '../../batDirectiveView/bt-creteproduct/bt-createproduct.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    BatProductRouting,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,
    BtCreateProductModule 
    
  ],
  declarations: [BatProductComponent, ],
  exports: [],
  providers: [ProductsService, FieldValidation, NgxSpinnerService]
})
export class BatProductModule { }
