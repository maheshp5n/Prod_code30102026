import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { CreateNewCircleComponent } from './create-new-circle.component';


describe('CreateNewCircleComponent', () => {
  let component: CreateNewCircleComponent;
  let fixture: ComponentFixture<CreateNewCircleComponent>;
  

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateNewCircleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateNewCircleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
