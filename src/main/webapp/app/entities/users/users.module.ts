import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MAgentsSharedModule } from 'app/shared/shared.module';
import { UsersComponent } from './users.component';
import { UsersDetailComponent } from './users-detail.component';
import { UsersUpdateComponent } from './users-update.component';
import { UsersDeleteDialogComponent } from './users-delete-dialog.component';
import { usersRoute } from './users.route';

@NgModule({
  imports: [MAgentsSharedModule, RouterModule.forChild(usersRoute)],
  declarations: [UsersComponent, UsersDetailComponent, UsersUpdateComponent, UsersDeleteDialogComponent],
  entryComponents: [UsersDeleteDialogComponent],
})
export class MAgentsUsersModule {}
