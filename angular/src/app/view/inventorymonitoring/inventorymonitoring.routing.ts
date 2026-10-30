import { Routes, RouterModule } from "@angular/router";
import { InventorymonitoringComponent } from './inventorymonitoring.component';
import { NgModule } from '@angular/core';
import { StocksummaryComponent } from './stocksummary/stocksummary.component';
import { StocksComponent } from './stocks/stocks.component';
import { HresholdComponent } from './hreshold/hreshold.component';

const routes:Routes=[{
    path:'',
    component:InventorymonitoringComponent,
    children:[
        {
            path:'stocksummary',
            component:StocksummaryComponent
        },
        {
            path:'stocks',
            component:StocksComponent
        },
        {
            path:'stocks/:id',
            component:StocksComponent
        },
        {
            path:'threshhold',
            component:HresholdComponent
        },
        {
            path:'threshhold/:id',
            component:HresholdComponent
        },
        {
            path:'',redirectTo:'stocksummary',pathMatch:'full'
        }
    ]

}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class InventorymonitoringRouting{}