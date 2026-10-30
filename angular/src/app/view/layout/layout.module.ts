import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LayoutComponent } from './layout.component';
import { FormsModule } from '@angular/forms';
import { LayoutroutingModule } from './layout.routing';
import { PingPoingService } from '../../services/ping.service';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { WabsocketService } from '../../services/wabsocket.service';
import { LayoutService } from '../../services/layout.service';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { ModalModule, TabsModule } from 'ngx-bootstrap';
import { ChangePasswordService } from '../../services/change-password.service';
import { AppBreadcrumbModule, AppAsideModule, AppFooterModule, AppHeaderModule, AppSidebarModule } from '@coreui/angular';
import { PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import { ChartsModule } from 'ng2-charts';
import { Loginservices } from '../../services/login.services';
import { P403Component } from '../../p403/p403.component';
import { SearchresultdisplayModule } from '../../DirectivesView/searchresultdisplay/searchresultdisplay.module';
import { RecursiveSearchModule } from '../../pipe/recursive-search.pipe.module';
import { UsernoteComponent } from './usernote/usernote.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    LayoutroutingModule,
    BsDropdownModule,
    ModalModule,
    NgxSpinnerModule,
    AppAsideModule,
    AppBreadcrumbModule,
    AppFooterModule,
    AppHeaderModule,
    AppSidebarModule,
    PerfectScrollbarModule,
    BsDropdownModule,
    TabsModule,
    ChartsModule,
    SearchresultdisplayModule,
    RecursiveSearchModule
  ],
  declarations: [LayoutComponent, P403Component, UsernoteComponent],
  providers: [PingPoingService, WabsocketService, LayoutService, NgxSpinnerService, ChangePasswordService, Loginservices]
})
export class LayoutModule { }
