import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { BatteryinventoryconsumptionComponent } from './batteryinventoryconsumption.component';

export const routes:Routes=[{
    path:'',component:BatteryinventoryconsumptionComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class Batteryinventoryconsumptionrouting{}