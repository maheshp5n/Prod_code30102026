import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ObjecthistoryfullComponent } from './objecthistoryfull.component';
import { ObjecthistoryfullRouting } from './objecthistoryfill.routing';



@NgModule({
  declarations: [ObjecthistoryfullComponent],
  imports: [
    CommonModule,
    ObjecthistoryfullRouting
  ]
})
export class ObjecthistoryfullModule { }
