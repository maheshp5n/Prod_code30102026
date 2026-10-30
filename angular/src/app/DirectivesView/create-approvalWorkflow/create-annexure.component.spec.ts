import { CreateAnnexureComponent } from '../create-annexure/create-annexure.component';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';


describe('CreateAnnexureComponent', () => {
  let component: CreateAnnexureComponent;
  let fixture: ComponentFixture<CreateAnnexureComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [CreateAnnexureComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateAnnexureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
