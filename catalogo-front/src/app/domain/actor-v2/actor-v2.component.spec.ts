import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActorsV2ViewModelService } from './actor-v2.component';

describe('ActorV2Component', () => {
  let component: ActorsV2ViewModelService;
  let fixture: ComponentFixture<ActorsV2ViewModelService>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActorsV2ViewModelService]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActorsV2ViewModelService);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
