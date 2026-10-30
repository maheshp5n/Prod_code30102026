import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { BatteryFSRCBMSLiLonbatterydetailsComponent } from './battery-fsrcbmsli-lonbatterydetails.component';

export const routes:Routes=[{
    path:'',component:BatteryFSRCBMSLiLonbatterydetailsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatteryFSRCBMSLiLonbatterydetailsRouting{}