import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductConsumerComponent } from './product-consumer.component';

describe('ProductConsumerComponent', () => {
  let component: ProductConsumerComponent;
  let fixture: ComponentFixture<ProductConsumerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductConsumerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductConsumerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
