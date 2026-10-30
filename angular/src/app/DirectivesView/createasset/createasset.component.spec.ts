import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateassetComponent } from './createasset.component';

describe('CreateassetComponent', () => {
  let component: CreateassetComponent;
  let fixture: ComponentFixture<CreateassetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateassetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateassetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
