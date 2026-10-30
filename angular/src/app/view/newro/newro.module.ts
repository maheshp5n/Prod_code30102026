import { NewroComponent } from './newro.component';
import { NewroRouting } from './newro.routing';
import {
  ProductreturnorderdirectiveModule
} from '../../DirectivesView/productreturnorderdirective/productreturnorderdirective.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';



@NgModule({
  declarations: [NewroComponent],
  imports: [
    CommonModule,
    NewroRouting,
    ProductreturnorderdirectiveModule
  ]
})
export class NewroModule { }
