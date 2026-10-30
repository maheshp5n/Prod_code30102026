import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateRpairDCTComponent } from './create-rpair-dct.component';

describe('CreateRpairDCTComponent', () => {
  let component: CreateRpairDCTComponent;
  let fixture: ComponentFixture<CreateRpairDCTComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateRpairDCTComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateRpairDCTComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
