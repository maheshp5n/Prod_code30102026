import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { NewsiteComponent } from './newsite.component';

describe('NewsiteComponent', () => {
  let component: NewsiteComponent;
  let fixture: ComponentFixture<NewsiteComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ NewsiteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewsiteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
