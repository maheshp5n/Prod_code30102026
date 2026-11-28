import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { CreateNewBoardingComponent } from './create-new-boarding.component';


describe('CreateNewBoardingComponent', () => {
  let component: CreateNewBoardingComponent;
  let fixture: ComponentFixture<CreateNewBoardingComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [CreateNewBoardingComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateNewBoardingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
