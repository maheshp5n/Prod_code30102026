import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { ReportComponent } from "./report.component";
import { LazyreportexicuationComponent } from "./lazyreportexicuation/lazyreportexicuation.component";
import { LazyreportpersentationComponent } from "./lazyreportpersentation/lazyreportpersentation.component";


const routes: Routes = [
    {
        path:'',redirectTo:'reporte'
    },
    {
        path: '', component: ReportComponent,children:
        [
            {
                path:'reporte',
                component:LazyreportexicuationComponent
            },
            {
                path:'reportp',
                component:LazyreportpersentationComponent
            }
        ]
    }
    

]
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})

export class ReportRouting {

}