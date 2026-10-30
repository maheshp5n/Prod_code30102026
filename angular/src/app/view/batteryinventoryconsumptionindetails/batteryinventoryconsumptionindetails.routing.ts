import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { BatteryinventoryconsumptionindetailsComponent } from './batteryinventoryconsumptionindetails.component';

export const routes:Routes=[{
    path:'',component:BatteryinventoryconsumptionindetailsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class Batteryinventoryconsumptionindetailsrouting{}