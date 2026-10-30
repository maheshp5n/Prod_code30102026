import { Component, Input, OnInit, OnDestroy, OnChanges, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { SapDocumentService } from '../../bat_services/bat-sap-doc-service';
import { GlobalserviceService } from '../../../globalservice.service';

@Component({
  selector: 'app-bat-sap-document-directive',
  templateUrl: './bat-sap-document-directive.component.html',
  styleUrls: ['./bat-sap-document-directive.component.scss']
})
export class BatSapDocumentDirectiveComponent implements OnInit, OnDestroy, OnChanges {

  @ViewChild('objectPdfView', { static: false }) objectPdfView: ModalDirective;

  @Input() set crmDocNumber(doc) {
    if (doc) {
      this.getStkHdrDoc(doc);
      this.getStoHdr(doc);
    }
  }

  @Input() isStoDocEnabled: boolean = false;

  pdfViewerUrlMap: string[] = [];

  it = {
    title: '',
    doc: []
  };

  stk = [];
  sto = [];
  isModelShow = false;

  constructor(
    private _sap: SapDocumentService,
    private _global: GlobalserviceService
  ) { }

  ngOnInit() {
  }

  ngOnChanges() {
  }

  ngOnDestroy() {
    this.cleanupBlobUrls();
  }

  getStkHdrDoc(crmdocnumber) {
    this._sap.getSkHdr(crmdocnumber).subscribe(data => this.stk = data);
  }

  getStoHdr(crmdocnumber) {
    this._sap.getStoHdr(crmdocnumber).subscribe(data => this.sto = data);
  }

  stkdisplay(item) {
    this.it.doc = [];
    this.it.title = 'INVOICE NO ' + item.INVOICE_NO;
    this.it.doc.push({ data: 'data:application/pdf;base64,' + item.Invoice_Doc });
    this.objectPdfView.show();
    this.isModelShow = true;
    return false;
  }

  stodisplay(item) {
    this.cleanupBlobUrls();
    this.it.doc = [];
    this.pdfViewerUrlMap = [];
    this.it.title = 'INVOICE NO ' + item.INVOICE;

    const docs = [
      item.Document1,
      item.Document2,
      item.Document3,
      item.Document4
    ];

    const allBlobUrls = [];

    docs.forEach((doc) => {
      if (!doc) {
        return;
      }

      try {
        const blobUrl = this.base64ToBlobUrl(doc);
        allBlobUrls.push(blobUrl);

        let viewerUrl = '';

        if (this.isStoDocEnabled) {
          viewerUrl = `/assets/pdfjs/viewr.html?file=${encodeURIComponent(blobUrl)}&mode=full&invoice=${encodeURIComponent(item.INVOICE)}`;
        } else {
          viewerUrl = `/assets/pdfjs/viewr.html?file=${encodeURIComponent(blobUrl)}&mode=readonly&invoice=${encodeURIComponent(item.INVOICE)}`;
        }

        this.pdfViewerUrlMap.push(viewerUrl);

      } catch (error) {
      }
    });

    (window as any).pdfUrls = allBlobUrls;

    this.objectPdfView.show();
    this.isModelShow = true;
  }

  private base64ToBlobUrl(base64: string): string {
    const cleaned = base64.replace(/^data:application\/pdf;base64,/, '');
    const byteCharacters = atob(cleaned);
    const byteNumbers = new Array(byteCharacters.length);

    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }

    const byteArray = new Uint8Array(byteNumbers);
    const blob = new Blob([byteArray], { type: 'application/pdf' });
    const blobUrl = URL.createObjectURL(blob);

    return blobUrl;
  }

  private cleanupBlobUrls() {
    this.pdfViewerUrlMap.forEach((url) => {
      try {
        const urlParts = url.split('file=');
        if (urlParts.length > 1) {
          const encodedBlob = urlParts[1].split('&')[0];
          const blobUrl = decodeURIComponent(encodedBlob);

          if (blobUrl && blobUrl.startsWith('blob:')) {
            URL.revokeObjectURL(blobUrl);
          }
        }
      } catch (error) {
      }
    });

    this.pdfViewerUrlMap = [];
  }

  hideModal() {
    this.cleanupBlobUrls();
    this.it.doc = [];
    this.objectPdfView.hide();
    this.isModelShow = false;
  }
}
