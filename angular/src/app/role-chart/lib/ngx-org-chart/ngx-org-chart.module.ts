
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { NgxChartDesignerModule } from '../ngx-chart-designer/ngx-chart-designer.module';
import { NgxOrgChartComponent } from './ngx-org-chart.component';
import { CommonModule } from '@angular/common';

@NgModule({
  declarations: [
    NgxOrgChartComponent
  ],
  imports: [
    CommonModule,
    NgxChartDesignerModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [NgxOrgChartComponent]
})
export class NgxOrgChartModule { }
