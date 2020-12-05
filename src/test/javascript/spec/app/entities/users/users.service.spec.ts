import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { UsersService } from 'app/entities/users/users.service';
import { IUsers, Users } from 'app/shared/model/users.model';

describe('Service Tests', () => {
  describe('Users Service', () => {
    let injector: TestBed;
    let service: UsersService;
    let httpMock: HttpTestingController;
    let elemDefault: IUsers;
    let expectedResult: IUsers | IUsers[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UsersService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Users('ID', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateAssigned: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find('123').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Users', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            dateAssigned: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateAssigned: currentDate,
          },
          returnedFromService
        );

        service.create(new Users()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Users', () => {
        const returnedFromService = Object.assign(
          {
            userID: 'BBBBBB',
            username: 'BBBBBB',
            password: 'BBBBBB',
            roles: 'BBBBBB',
            dateAssigned: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateAssigned: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Users', () => {
        const returnedFromService = Object.assign(
          {
            userID: 'BBBBBB',
            username: 'BBBBBB',
            password: 'BBBBBB',
            roles: 'BBBBBB',
            dateAssigned: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateAssigned: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Users', () => {
        service.delete('123').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
