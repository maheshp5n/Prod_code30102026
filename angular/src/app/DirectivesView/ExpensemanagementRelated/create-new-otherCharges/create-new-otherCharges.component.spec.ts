import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { CreateNewOtherChargesComponent } from './create-new-otherCharges.component';


describe('CreateNewOtherChargesComponent', () => {
  let component: CreateNewOtherChargesComponent;
  let fixture: ComponentFixture<CreateNewOtherChargesComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [CreateNewOtherChargesComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateNewOtherChargesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
