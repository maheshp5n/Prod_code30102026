import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginRoutingModule } from './login.routing.module';
import { LoginComponent } from './login.component';
import { Loginservices } from '../services/login.services';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap';

@NgModule({
  imports: [
    CommonModule,
    LoginRoutingModule,
    FormsModule,
    NgxSpinnerModule,
    ModalModule
  ],
  providers:[Loginservices,NgxSpinnerService],
  declarations: [LoginComponent]
})
export class LoginModule { }
