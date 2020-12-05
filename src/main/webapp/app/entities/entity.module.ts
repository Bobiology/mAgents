import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'transaction',
        loadChildren: () => import('./transaction/transaction.module').then(m => m.MAgentsTransactionModule),
      },
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.MAgentsCustomerModule),
      },
      {
        path: 'users',
        loadChildren: () => import('./users/users.module').then(m => m.MAgentsUsersModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class MAgentsEntityModule {}
