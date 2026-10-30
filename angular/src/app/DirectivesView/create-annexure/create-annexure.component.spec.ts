import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateAnnexureComponent } from './create-annexure.component';

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
