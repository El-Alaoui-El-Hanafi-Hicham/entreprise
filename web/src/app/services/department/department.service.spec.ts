import { TestBed } from '@angular/core/testing';
import { departmentService } from './department.service';


describe('DepartmentService', () => {
  let service: departmentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(departmentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
