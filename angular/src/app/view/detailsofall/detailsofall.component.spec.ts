import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { DetailsofallComponent } from './detailsofall.component';

describe('DetailsofallComponent', () => {
  let component: DetailsofallComponent;
  let fixture: ComponentFixture<DetailsofallComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ DetailsofallComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetailsofallComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
