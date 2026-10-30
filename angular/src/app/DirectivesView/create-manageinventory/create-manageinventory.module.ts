import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalModule, BsDatepickerModule, TimepickerModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { CreateManageinventoryComponent } from './create-manageinventory.component';
import { ManageInventoryService } from '../../services/manageinventory.services';
import { CreateNewProductModule } from '../create-new-product/create-new-product.module';
import { AssetsService } from '../../services/assets.services';
import { CreateassetModule } from '../createasset/createasset.module';
import { CreateLegalEntityModule } from '../createlegalentity/createlegalentity.module';
import { ligalEntityService } from '../../services/ligalentity.services';
import { CreateSiteModule } from '../create-site/create-site.module';
import { ContactcreateModule } from '../contactcreate/contactcreate.module';
import { CreatecaseModule } from '../createcase/createcase.module';
import { Routes, RouterModule } from '@angular/router';
import { NgSelectModule } from '@ng-select/ng-select';

const routes: Routes = [
  {
    path: '', component: CreateManageinventoryComponent
  }
]

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ModalModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    NgxSpinnerModule,
    CreateNewProductModule,
    CreateassetModule,
    CreateLegalEntityModule,
    CreateSiteModule,
    ContactcreateModule,
    CreatecaseModule,
    RouterModule.forChild(routes),
    NgSelectModule

  ],
  declarations: [CreateManageinventoryComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [CreateManageinventoryComponent,RouterModule],
  providers: [NgxSpinnerService, ManageInventoryService, AssetsService, ligalEntityService,DatePipe]
})
export class CreateManageinventoryModule { }
