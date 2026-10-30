import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { NgxChartNodeComponent } from './ngx-chart-node.component';
import { CommonModule } from '@angular/common';

@NgModule({
  declarations: [
    NgxChartNodeComponent
  ],
  imports: [
    CommonModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [NgxChartNodeComponent]
})
export class NgxChartNodeModule { }
