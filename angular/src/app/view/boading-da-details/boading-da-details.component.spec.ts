import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BoadingDaDetailsComponent } from './boading-da-details.component';

describe('BoadingDaDetailsComponent', () => {
  let component: BoadingDaDetailsComponent;
  let fixture: ComponentFixture<BoadingDaDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BoadingDaDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BoadingDaDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
