import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { BulkuploadComponent } from './bulkupload.component';

const routes:Routes=[
    {
        path:'',component:BulkuploadComponent
    }
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BulkUploadRouting{

}