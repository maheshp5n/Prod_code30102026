import { CreateNewTourExpensesComponent } from './create-new-tourExpenses.component';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';


describe('CreateNewTourExpensesComponent', () => {
  let component: CreateNewTourExpensesComponent;
  let fixture: ComponentFixture<CreateNewTourExpensesComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [CreateNewTourExpensesComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateNewTourExpensesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
