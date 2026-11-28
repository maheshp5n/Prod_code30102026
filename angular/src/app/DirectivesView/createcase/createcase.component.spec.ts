import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CreatecaseComponent } from './createcase.component';

describe('CreatecaseComponent', () => {
  let component: CreatecaseComponent;
  let fixture: ComponentFixture<CreatecaseComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CreatecaseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreatecaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
