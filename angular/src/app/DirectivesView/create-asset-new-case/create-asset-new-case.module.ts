import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CreateAssetNewCaseComponent } from './create-asset-new-case.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    
  ],
  declarations: [CreateAssetNewCaseComponent],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  exports:[CreateAssetNewCaseComponent],
  providers:[]

})
export class CreateAssetNewCaseModule { }
