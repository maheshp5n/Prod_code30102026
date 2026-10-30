
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { NgxChartNodeModule } from '../ngx-chart-node/ngx-chart-node.module';
import { NgxChartDesignerComponent } from './ngx-chart-designer.component';
import { CommonModule } from '@angular/common';

@NgModule({
  declarations: [
    NgxChartDesignerComponent
  ],
  imports: [
    CommonModule,
    NgxChartNodeModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [NgxChartDesignerComponent]
})
export class NgxChartDesignerModule { }
