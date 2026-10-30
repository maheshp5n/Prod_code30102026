import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateAssetNewCaseComponent } from './create-asset-new-case.component';

describe('CreateAssetNewCaseComponent', () => {
  let component: CreateAssetNewCaseComponent;
  let fixture: ComponentFixture<CreateAssetNewCaseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateAssetNewCaseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateAssetNewCaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
