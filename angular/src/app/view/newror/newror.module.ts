import { NewrorComponent } from './newror.component';
import { NewrorRouting } from './newror.routing';
import {
  ProductreturnreceivedirectiveModule
} from '../../DirectivesView/productreturnreceivedirective/productreturnreceivedirective.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';



@NgModule({
  declarations: [NewrorComponent],
  imports: [
    CommonModule,
    NewrorRouting,
    ProductreturnreceivedirectiveModule
  ]
})
export class NewrorModule { }
