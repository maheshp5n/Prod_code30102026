import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { CreateNewLodgingComponent } from './create-new-lodging.component';


describe('CreateNewLodgingComponent', () => {
  let component: CreateNewLodgingComponent;
  let fixture: ComponentFixture<CreateNewLodgingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [CreateNewLodgingComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateNewLodgingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
