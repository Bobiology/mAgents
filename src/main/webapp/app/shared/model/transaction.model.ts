import { Moment } from 'moment';
import { TransType } from 'app/shared/model/enumerations/trans-type.model';

export interface ITransaction {
  id?: string;
  transReference?: string;
  transAmount?: number;
  currency?: number;
  transType?: TransType;
  transStatus?: string;
  custID?: string;
  transDate?: Moment;
}

export class Transaction implements ITransaction {
  constructor(
    public id?: string,
    public transReference?: string,
    public transAmount?: number,
    public currency?: number,
    public transType?: TransType,
    public transStatus?: string,
    public custID?: string,
    public transDate?: Moment
  ) {}
}
