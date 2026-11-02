import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import { PERFECT_SCROLLBAR_CONFIG } from 'ngx-perfect-scrollbar';
import { PerfectScrollbarConfigInterface } from 'ngx-perfect-scrollbar';

const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  suppressScrollX: true
};

import { AppComponent } from './app.component';

// Import containers
import { DefaultLayoutComponent } from './containers';

import { P404Component } from './views/error/404.component';
import { P500Component } from './views/error/500.component';
import { LoginComponent } from './views/login/login.component';
import { RegisterComponent } from './views/register/register.component';

const APP_CONTAINERS = [
  DefaultLayoutComponent
];

import { AppAsideModule, AppBreadcrumbModule, AppHeaderModule, AppFooterModule, AppSidebarModule, } from '@coreui/angular';

// Import routing module
import { AppRoutingModule } from './app.routing';

// Import 3rd party components
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { TabsModule } from 'ngx-bootstrap/tabs';
import { ChartsModule } from 'ng2-charts';
import { ChangePasswordService } from './services/change-password.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { AlertModule } from 'ngx-bootstrap/alert';
import { AccordionModule } from 'ngx-bootstrap/accordion';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { LayoutgaurdGuard } from './gaurd/layoutgaurd.guard';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { GoogleChartsModule } from 'angular-google-charts';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
import { SwitchComponent } from './switch/switch.component';
import { AppHttpIntercepter } from './httpinterceptor/httpinterceptor';
import { ForgetpassComponent } from './forgetpass/forgetpass.component';
import { FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    AppAsideModule,
    AppBreadcrumbModule.forRoot(),
    AppFooterModule,
    AppHeaderModule,
    AppSidebarModule,
    PerfectScrollbarModule,
    HttpClientModule,
    BsDropdownModule.forRoot(),
    TabsModule.forRoot(),
    ChartsModule,
    BsDatepickerModule.forRoot(),
    ModalModule.forRoot(),
    NgxSpinnerModule,
    AlertModule.forRoot(),
    AccordionModule.forRoot(),
    TypeaheadModule.forRoot(),
    TooltipModule.forRoot(),
    GoogleChartsModule.forRoot(),
    PaginationModule.forRoot(),
    FormsModule,
    ServiceWorkerModule.register('ngsw-worker.js', { enabled: environment.production })
  ],
  declarations: [
    AppComponent,
    ...APP_CONTAINERS,
    P404Component,
    P500Component,
    LoginComponent,
    RegisterComponent,
    SwitchComponent,
    ForgetpassComponent

  ],
  providers: [
    LayoutgaurdGuard,
    { provide: LocationStrategy, useClass: HashLocationStrategy },
    ChangePasswordService,
    { provide: HTTP_INTERCEPTORS, useClass: AppHttpIntercepter, multi: true },
    NgxSpinnerService, ReactiveFormsModule, FormBuilder
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
