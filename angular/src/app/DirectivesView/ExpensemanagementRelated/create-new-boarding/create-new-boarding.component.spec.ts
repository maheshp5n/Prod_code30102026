import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { CreateNewBoardingComponent } from './create-new-boarding.component';


describe('CreateNewBoardingComponent', () => {
  let component: CreateNewBoardingComponent;
  let fixture: ComponentFixture<CreateNewBoardingComponent>;

  beforeEach(async(() => {
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
