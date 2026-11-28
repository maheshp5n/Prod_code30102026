import { CreateAnnexureComponent } from '../create-annexure/create-annexure.component';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';


describe('CreateAnnexureComponent', () => {
  let component: CreateAnnexureComponent;
  let fixture: ComponentFixture<CreateAnnexureComponent>;

  beforeEach(waitForAsync(() => {
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
