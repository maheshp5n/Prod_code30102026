import { CreateNewTourExpensesComponent } from './create-new-tourExpenses.component';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';


describe('CreateNewTourExpensesComponent', () => {
  let component: CreateNewTourExpensesComponent;
  let fixture: ComponentFixture<CreateNewTourExpensesComponent>;

  beforeEach(async(() => {
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
