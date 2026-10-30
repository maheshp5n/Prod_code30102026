import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { AnnexuredetailsComponent } from './annexuredetails.component';

describe('AnnexuredetailsComponent', () => {
  let component: AnnexuredetailsComponent;
  let fixture: ComponentFixture<AnnexuredetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AnnexuredetailsComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnnexuredetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
