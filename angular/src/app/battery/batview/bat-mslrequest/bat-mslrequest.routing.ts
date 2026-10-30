import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { BatMslrequestComponent } from './bat-mslrequest.component';
const routes: Routes = [{
    path: '', component: BatMslrequestComponent
}];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatMslrequestRouting {

}
