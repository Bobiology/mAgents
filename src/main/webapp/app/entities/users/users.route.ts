import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUsers, Users } from 'app/shared/model/users.model';
import { UsersService } from './users.service';
import { UsersComponent } from './users.component';
import { UsersDetailComponent } from './users-detail.component';
import { UsersUpdateComponent } from './users-update.component';

@Injectable({ providedIn: 'root' })
export class UsersResolve implements Resolve<IUsers> {
  constructor(private service: UsersService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUsers> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((users: HttpResponse<Users>) => {
          if (users.body) {
            return of(users.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Users());
  }
}

export const usersRoute: Routes = [
  {
    path: '',
    component: UsersComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Users',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UsersDetailComponent,
    resolve: {
      users: UsersResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Users',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UsersUpdateComponent,
    resolve: {
      users: UsersResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Users',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UsersUpdateComponent,
    resolve: {
      users: UsersResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Users',
    },
    canActivate: [UserRouteAccessService],
  },
];
