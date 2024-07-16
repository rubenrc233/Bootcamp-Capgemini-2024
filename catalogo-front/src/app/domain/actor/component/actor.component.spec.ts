import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActorsComponent } from './actor.component';


describe('ActorComponent', () => {
  let component: ActorsComponent;
  let fixture: ComponentFixture<ActorsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActorsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActorsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
