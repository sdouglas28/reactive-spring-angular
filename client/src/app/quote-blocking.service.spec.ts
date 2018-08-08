import { TestBed, inject } from '@angular/core/testing';

import { QuoteBlockingService } from './quote-blocking.service';

describe('QuoteBlockingService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [QuoteBlockingService]
    });
  });

  it('should be created', inject([QuoteBlockingService], (service: QuoteBlockingService) => {
    expect(service).toBeTruthy();
  }));
});
