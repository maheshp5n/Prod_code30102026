import { CommonModule } from "@angular/common";
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from "@angular/core";
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { URLSafeModule } from "../../../pipe/url-safe-pipe.module";
import { BatSapDocumentDirectiveComponent } from "./bat-sap-document-directive.component";


@NgModule({
    declarations: [BatSapDocumentDirectiveComponent],
    imports: [
      CommonModule,
      BsDropdownModule,
      ModalModule,
      URLSafeModule
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    exports: [BatSapDocumentDirectiveComponent]
  })
  export class BatSapDocumentDirectiveModule{}