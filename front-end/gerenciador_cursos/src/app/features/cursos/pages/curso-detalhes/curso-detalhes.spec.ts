import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CursoDetalhes } from './curso-detalhes';

describe('CursoDetalhes', () => {
  let component: CursoDetalhes;
  let fixture: ComponentFixture<CursoDetalhes>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CursoDetalhes],
    }).compileComponents();

    fixture = TestBed.createComponent(CursoDetalhes);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
