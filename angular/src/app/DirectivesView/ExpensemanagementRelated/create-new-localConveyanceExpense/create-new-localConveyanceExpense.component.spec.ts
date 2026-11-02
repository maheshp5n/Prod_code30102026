import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { CreateNewLocalConveyanceExpenseComponent } from './create-new-localConveyanceExpense.component';


describe('CreateNewLocalConveyanceExpenseComponent', () => {
  let component: CreateNewLocalConveyanceExpenseComponent;
  let fixture: ComponentFixture<CreateNewLocalConveyanceExpenseComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [CreateNewLocalConveyanceExpenseComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateNewLocalConveyanceExpenseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
