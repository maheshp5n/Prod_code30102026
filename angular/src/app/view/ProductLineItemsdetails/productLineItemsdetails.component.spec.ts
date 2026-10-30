import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { CirclesindetailsComponent } from '../circlesindetails/circlesindetails.component';


describe('CirclesindetailsComponent', () => {
  let component: CirclesindetailsComponent;
  let fixture: ComponentFixture<CirclesindetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CirclesindetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CirclesindetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
