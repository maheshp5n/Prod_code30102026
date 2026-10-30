import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { CreateNewProductLineItemsComponent } from './create-new-productLineItems.component';


describe('CreateNewProductLineItemsComponent', () => {
  let component: CreateNewProductLineItemsComponent;
  let fixture: ComponentFixture<CreateNewProductLineItemsComponent>;
  

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateNewProductLineItemsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateNewProductLineItemsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
