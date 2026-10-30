import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { BatReportComponent } from './bat-report.component';
import { BatreportexecutionComponent } from './batreportexecution/batreportexecution.component';
import { BatreportpersentationComponent } from './batreportpersentation/batreportpersentation.component';
const routes: Routes = [{
    path: '', component: BatReportComponent, children:
  
        [
            {
                path:'reporte',
                component:BatreportexecutionComponent
            },
            {
                path:'reportp',
                component:BatreportpersentationComponent
            }
        ]
    
}];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatReportRouting {

}
