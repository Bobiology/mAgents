import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUsers } from 'app/shared/model/users.model';

type EntityResponseType = HttpResponse<IUsers>;
type EntityArrayResponseType = HttpResponse<IUsers[]>;

@Injectable({ providedIn: 'root' })
export class UsersService {
  public resourceUrl = SERVER_API_URL + 'api/users';

  constructor(protected http: HttpClient) {}

  create(users: IUsers): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(users);
    return this.http
      .post<IUsers>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(users: IUsers): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(users);
    return this.http
      .put<IUsers>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IUsers>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUsers[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(users: IUsers): IUsers {
    const copy: IUsers = Object.assign({}, users, {
      dateAssigned: users.dateAssigned && users.dateAssigned.isValid() ? users.dateAssigned.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateAssigned = res.body.dateAssigned ? moment(res.body.dateAssigned) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((users: IUsers) => {
        users.dateAssigned = users.dateAssigned ? moment(users.dateAssigned) : undefined;
      });
    }
    return res;
  }
}
