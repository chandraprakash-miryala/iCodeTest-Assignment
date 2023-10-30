import { TestBed } from '@angular/core/testing';

import { OrgCrudService } from './org-crud.service';

describe('OrgCrudService', () => {
  let service: OrgCrudService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrgCrudService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
