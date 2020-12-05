import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUsers } from 'app/shared/model/users.model';
import { UsersService } from './users.service';

@Component({
  templateUrl: './users-delete-dialog.component.html',
})
export class UsersDeleteDialogComponent {
  users?: IUsers;

  constructor(protected usersService: UsersService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.usersService.delete(id).subscribe(() => {
      this.eventManager.broadcast('usersListModification');
      this.activeModal.close();
    });
  }
}
