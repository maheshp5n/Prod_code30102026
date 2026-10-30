import { NgModule,CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CreateSiteNewCaseComponent } from './create-site-new-case.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule
  ],
  declarations: [CreateSiteNewCaseComponent],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  exports:[CreateSiteNewCaseComponent],
  providers:[]
})
export class CreateSiteNewCaseModule { }
