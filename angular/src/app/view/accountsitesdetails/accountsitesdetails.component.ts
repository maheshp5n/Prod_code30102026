import { NgxSpinnerService } from 'ngx-spinner';
import { AccountService } from '../../services/accounts.services';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-accountsitesdetails',
  templateUrl: './accountsitesdetails.component.html',
  styleUrls: ['./accountsitesdetails.component.scss']
})
export class AccountsitesdetailsComponent implements OnInit {

  accountdetail;
  tblheader;
  casedetails;
  accountsapnumber;
  constructor(
    private _ngxSpinner: NgxSpinnerService,
    private _accountService: AccountService
  ) { }

  ngOnInit() {
  }

}
