import { SapDocServices } from '../../services/sapdocservices';
import { ModalDirective } from 'ngx-bootstrap';
import { ViewChild } from '@angular/core';
import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sapdocdisplay',
  templateUrl: './sapdocdisplay.component.html',
  styleUrls: ['./sapdocdisplay.component.scss']
})
export class SapdocdisplayComponent implements OnInit {

  @ViewChild('objectPdfView', { static: false }) objectPdfView: ModalDirective;

  @Input() set crmDocNumber(doc) {
    if (doc) {
      console.log(doc)
      this.getStkHdrDoc(doc);
      this.getStoHdr(doc);
    }

  }

  it = {
    title: '',
    doc: []
  };
  stk = [];
  sto = [];
  isModelShow = false;
  constructor(private _sap: SapDocServices) { }

  ngOnInit() {
  }

  getStkHdrDoc(crmdocnumber) {
    this._sap.getStkHdr(crmdocnumber).subscribe(data => this.stk = data);
  }
  getStoHdr(crmdocnumber) {
    this._sap.getStoHdr(crmdocnumber).subscribe(data => this.sto = data);
  }
  stkdisplay(item) {
    this.it.doc=[];
    this.it.title = 'INVOICE NO ' + item.INVOICE_NO;
    this.it.doc.push({data:'data:application/pdf;base64,' + item.Invoice_Doc}) ;
    this.objectPdfView.show();
    this.isModelShow = true;
    return false;
  }
  stodisplay(item) {
    this.it.doc=[];
    this.it.title = 'INVOICE NO ' + item.INVOICE;
    this.it.doc.push({data:'data:application/pdf;base64,' + item.Document1});
    this.it.doc.push({data:'data:application/pdf;base64,' + item.Document2});
    this.it.doc.push({data:'data:application/pdf;base64,' + item.Document3});
    this.it.doc.push({data:'data:application/pdf;base64,' + item.Document4});
    this.objectPdfView.show();
    this.isModelShow = true;
    return false;
  }
}
