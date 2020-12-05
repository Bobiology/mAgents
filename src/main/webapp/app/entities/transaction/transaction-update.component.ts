import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITransaction, Transaction } from 'app/shared/model/transaction.model';
import { TransactionService } from './transaction.service';

@Component({
  selector: 'jhi-transaction-update',
  templateUrl: './transaction-update.component.html',
})
export class TransactionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    transReference: [null, [Validators.required]],
    transAmount: [],
    currency: [],
    transType: [],
    transStatus: [],
    custID: [],
    transDate: [],
  });

  constructor(protected transactionService: TransactionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transaction }) => {
      if (!transaction.id) {
        const today = moment().startOf('day');
        transaction.transDate = today;
      }

      this.updateForm(transaction);
    });
  }

  updateForm(transaction: ITransaction): void {
    this.editForm.patchValue({
      id: transaction.id,
      transReference: transaction.transReference,
      transAmount: transaction.transAmount,
      currency: transaction.currency,
      transType: transaction.transType,
      transStatus: transaction.transStatus,
      custID: transaction.custID,
      transDate: transaction.transDate ? transaction.transDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transaction = this.createFromForm();
    if (transaction.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionService.update(transaction));
    } else {
      this.subscribeToSaveResponse(this.transactionService.create(transaction));
    }
  }

  private createFromForm(): ITransaction {
    return {
      ...new Transaction(),
      id: this.editForm.get(['id'])!.value,
      transReference: this.editForm.get(['transReference'])!.value,
      transAmount: this.editForm.get(['transAmount'])!.value,
      currency: this.editForm.get(['currency'])!.value,
      transType: this.editForm.get(['transType'])!.value,
      transStatus: this.editForm.get(['transStatus'])!.value,
      custID: this.editForm.get(['custID'])!.value,
      transDate: this.editForm.get(['transDate'])!.value ? moment(this.editForm.get(['transDate'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransaction>>): void {
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
