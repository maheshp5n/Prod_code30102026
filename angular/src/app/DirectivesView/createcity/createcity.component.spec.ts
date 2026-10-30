import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatecityComponent } from './createcity.component';

describe('CreatecityComponent', () => {
  let component: CreatecityComponent;
  let fixture: ComponentFixture<CreatecityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreatecityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreatecityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
