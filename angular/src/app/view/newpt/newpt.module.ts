import { NewptComponent } from './newpt.component';
import { NewptRouting } from './newpt.routing';
import {
  ProducttransferdirectiveModule
} from '../../DirectivesView/producttransferdirective/producttransferdirective.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';



@NgModule({
  declarations: [NewptComponent],
  imports: [
    CommonModule,
    NewptRouting,
    ProducttransferdirectiveModule
  ]
})
export class NewptModule { }
