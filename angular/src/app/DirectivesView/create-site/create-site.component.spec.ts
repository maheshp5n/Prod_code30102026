import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CreateSiteComponent } from './create-site.component';

describe('CreateSiteComponent', () => {
  let component: CreateSiteComponent;
  let fixture: ComponentFixture<CreateSiteComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateSiteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateSiteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
