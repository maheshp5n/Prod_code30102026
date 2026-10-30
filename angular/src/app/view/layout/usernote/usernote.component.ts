import { UsernotesServices } from '../../../services/usernote.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-usernote',
  templateUrl: './usernote.component.html',
  styleUrls: ['./usernote.component.scss']
})
export class UsernoteComponent implements OnInit {
  drawer = {
    state: false,
    cls: 'note',
    anglecls: 'fa fa-angle-double-left'
  }
  mis = {
    today: new Date(),
    nNote: false,
    notecontent: ''
  }
  notes = [];

  constructor(private _notes: UsernotesServices) { }

  ngOnInit() {
    this.mis.today = new Date();
    this.getNotes();
  }

  getNotes() {
    this._notes.getNotes().subscribe(data => this.notes = data);
  }
  postNote() {
    this._notes.createNotes({ notes: this.mis.notecontent }).subscribe(data => {
      this.notes = data;
      this.mis.notecontent = '';
      this.mis.nNote = false;
    });
  }

  transformDrawer() {
    this.drawer.state = !this.drawer.state;
    if (this.drawer.state) {
      this.drawer.cls = 'note note-extends';
      this.drawer.anglecls = 'fa fa-angle-double-right';
    }
    else {
      this.drawer.cls = 'note';
      this.drawer.anglecls = 'fa fa-angle-double-left';
    }
  }
  deleteNtoes(id) {
    this._notes.delteNote(id).subscribe(data => {

      this.getNotes();
    });
  }
}
