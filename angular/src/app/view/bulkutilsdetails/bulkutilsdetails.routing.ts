import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { BulkutilsdetailsComponent } from "./bulkutilsdetails.component";

const routes:Routes=[{
    path:'',
    component:BulkutilsdetailsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BulkUtilsDetailsRouting{}