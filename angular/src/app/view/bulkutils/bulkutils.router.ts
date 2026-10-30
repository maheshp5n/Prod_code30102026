import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { BulkutilsComponent } from "./bulkutils.component";

const routes:Routes=[{
    path:'',
    component:BulkutilsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BulkutilsRouting{}