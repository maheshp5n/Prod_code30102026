import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { AnnexureLineItemdetailsComponent } from './annexureLineItemdetails.component';

describe('AnnexureLineItemdetailsComponent', () => {
  let component: AnnexureLineItemdetailsComponent;
  let fixture: ComponentFixture<AnnexureLineItemdetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AnnexureLineItemdetailsComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnnexureLineItemdetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
