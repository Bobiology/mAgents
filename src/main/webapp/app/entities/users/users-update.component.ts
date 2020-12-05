import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IUsers, Users } from 'app/shared/model/users.model';
import { UsersService } from './users.service';

@Component({
  selector: 'jhi-users-update',
  templateUrl: './users-update.component.html',
})
export class UsersUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    userID: [null, [Validators.required]],
    username: [],
    password: [],
    roles: [],
    dateAssigned: [],
  });

  constructor(protected usersService: UsersService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ users }) => {
      if (!users.id) {
        const today = moment().startOf('day');
        users.dateAssigned = today;
      }

      this.updateForm(users);
    });
  }

  updateForm(users: IUsers): void {
    this.editForm.patchValue({
      id: users.id,
      userID: users.userID,
      username: users.username,
      password: users.password,
      roles: users.roles,
      dateAssigned: users.dateAssigned ? users.dateAssigned.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const users = this.createFromForm();
    if (users.id !== undefined) {
      this.subscribeToSaveResponse(this.usersService.update(users));
    } else {
      this.subscribeToSaveResponse(this.usersService.create(users));
    }
  }

  private createFromForm(): IUsers {
    return {
      ...new Users(),
      id: this.editForm.get(['id'])!.value,
      userID: this.editForm.get(['userID'])!.value,
      username: this.editForm.get(['username'])!.value,
      password: this.editForm.get(['password'])!.value,
      roles: this.editForm.get(['roles'])!.value,
      dateAssigned: this.editForm.get(['dateAssigned'])!.value
        ? moment(this.editForm.get(['dateAssigned'])!.value, DATE_TIME_FORMAT)
        : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUsers>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
