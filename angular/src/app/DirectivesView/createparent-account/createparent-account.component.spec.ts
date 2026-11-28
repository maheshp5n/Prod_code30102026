import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CreateparentAccountComponent } from './createparent-account.component';

describe('CreateparentAccountComponent', () => {
  let component: CreateparentAccountComponent;
  let fixture: ComponentFixture<CreateparentAccountComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateparentAccountComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateparentAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
