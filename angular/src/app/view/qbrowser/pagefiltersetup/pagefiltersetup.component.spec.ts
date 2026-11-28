import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { PagefiltersetupComponent } from './pagefiltersetup.component';

describe('PagefiltersetupComponent', () => {
  let component: PagefiltersetupComponent;
  let fixture: ComponentFixture<PagefiltersetupComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ PagefiltersetupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PagefiltersetupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
