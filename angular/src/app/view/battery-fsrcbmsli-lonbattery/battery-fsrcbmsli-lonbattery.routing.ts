import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { BatteryFSRCBMSLiLonbatteryComponent } from './battery-fsrcbmsli-lonbattery.component';

export const routes:Routes=[{
    path:'',component:BatteryFSRCBMSLiLonbatteryComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatteryFSRCBMSLiLonbatteryRouting{}