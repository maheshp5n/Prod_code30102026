import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CreateSiteEditComponent } from './create-site-edit.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule
  ],
  declarations: [CreateSiteEditComponent],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  exports:[CreateSiteEditComponent],
  providers:[]
})
export class CreateSiteEditModule { }
