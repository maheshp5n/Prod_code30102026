import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatCommercialDetailComponent } from './bat-commercial-detail.component';

describe('BatCommercialDetailComponent', () => {
  let component: BatCommercialDetailComponent;
  let fixture: ComponentFixture<BatCommercialDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatCommercialDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatCommercialDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
