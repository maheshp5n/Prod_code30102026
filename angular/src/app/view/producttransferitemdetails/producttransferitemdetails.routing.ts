import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { ProducttransferitemdetailsComponent } from './producttransferitemdetails.component';

const routs:Routes=[
    {
        path:'',
        component:ProducttransferitemdetailsComponent,
      
    }
]
@NgModule({
    imports:[RouterModule.forChild(routs)],
    exports:[RouterModule]
})
export class PTIDetailsRouting{

}