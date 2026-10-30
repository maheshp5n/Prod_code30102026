import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatEditGRNComponent } from './bat-edit-grn.component';

describe('BatEditGRNComponent', () => {
  let component: BatEditGRNComponent;
  let fixture: ComponentFixture<BatEditGRNComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatEditGRNComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatEditGRNComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
