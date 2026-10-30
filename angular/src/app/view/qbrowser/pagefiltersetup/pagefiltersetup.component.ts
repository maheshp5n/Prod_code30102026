import { PagefiltersetupService } from '../../../services/pagefiltersettup';
import { GlobalserviceService } from '../../../globalservice.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-pagefiltersetup',
  templateUrl: './pagefiltersetup.component.html',
  styleUrls: ['./pagefiltersetup.component.scss']
})
export class PagefiltersetupComponent implements OnInit {

  pages = [];
  profiles = [];
  filters = [];
  selectedddl = {
    pagetId: 0,
    profileId: 0
  };
  globalcheck = false;
  constructor(private _setupService: PagefiltersetupService, private _globalservice: GlobalserviceService) { }

  ngOnInit() {
    this.getPages();
    this.getprofiles();
  }

  getPages() {
    this._setupService.getAllUrls().subscribe(data => this.pages = data);
  }
  getprofiles() {
    this._setupService.getAllProfile().subscribe(data => this.profiles = data);
  }
  getFilters() {
    this.filters = [];
    this.globalcheck = false;
    this._setupService.getPageFilterByUrlAndProfile(this.selectedddl.profileId, this.selectedddl.pagetId).subscribe(data => {
      data.forEach(d => {
        if (d.usmid !== null) {
          d.selected = true;
        }
        this.filters.push(d);
      });
      this.filters = data;
    });
  }
  saveProfile() {
    this.filters.forEach(d => d.profileId = this.selectedddl.profileId);
    this._setupService.saveDetails(this.filters).subscribe(data => {
      this.getFilters()
    }, error => {
      this._globalservice.alert({ type: 'alert', message: error.error.message });
    });

  }

  chagneGlobalCheckBod() {
    this.filters.forEach(f => {
      f.selected = this.globalcheck;
    });
  }

}
