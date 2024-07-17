import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActorV2Component } from './actor-v2.component';

describe('ActorV2Component', () => {
  let component: ActorV2Component;
  let fixture: ComponentFixture<ActorV2Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActorV2Component]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActorV2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
