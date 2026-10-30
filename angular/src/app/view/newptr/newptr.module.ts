import { NewptrComponent } from './newptr.component';
import { NewptrRouting } from './newptr.routing';
import {
  ProductrequestreceiveModule
} from '../../DirectivesView/productrequestreceive/productrequestreceive.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';



@NgModule({
  declarations: [NewptrComponent],
  imports: [
    CommonModule,
    NewptrRouting,
    ProductrequestreceiveModule
  ]
})
export class NewptrModule { }
