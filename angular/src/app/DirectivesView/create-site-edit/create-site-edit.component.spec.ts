import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateSiteEditComponent } from './create-site-edit.component';

describe('CreateSiteEditComponent', () => {
  let component: CreateSiteEditComponent;
  let fixture: ComponentFixture<CreateSiteEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateSiteEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateSiteEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
