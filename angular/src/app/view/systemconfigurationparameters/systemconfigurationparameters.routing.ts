import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { SystemconfigurationparametersComponent } from './systemconfigurationparameters.component';
import { MailserverconfComponent } from './mailserverconf/mailserverconf.component';
import { SmsserverconfComponent } from './smsserverconf/smsserverconf.component';
import { TemplateconfComponent } from './templateconf/templateconf.component';

export const routes:Routes=[{
    path:'',component:SystemconfigurationparametersComponent,children:[
        {
            path:'mail',
            component:MailserverconfComponent
        },
        {
            path:'sms',
            component:SmsserverconfComponent
        },
        {
            path:'template',
            component:TemplateconfComponent
        },
        {
            path:'',
            redirectTo:'template',
            pathMatch:'full'
        }
    ]
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class SystemconfigurationparametersRouting{}