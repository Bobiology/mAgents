import { Moment } from 'moment';

export interface ICustomer {
  id?: string;
  customerID?: string;
  firstName?: string;
  lastName?: string;
  emailAddress?: string;
  homePhone?: string;
  mobilePhone?: string;
  attachmentContentType?: string;
  attachment?: any;
  notes?: string;
  dateCreated?: Moment;
}

export class Customer implements ICustomer {
  constructor(
    public id?: string,
    public customerID?: string,
    public firstName?: string,
    public lastName?: string,
    public emailAddress?: string,
    public homePhone?: string,
    public mobilePhone?: string,
    public attachmentContentType?: string,
    public attachment?: any,
    public notes?: string,
    public dateCreated?: Moment
  ) {}
}
