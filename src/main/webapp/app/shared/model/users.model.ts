import { Moment } from 'moment';

export interface IUsers {
  id?: string;
  userID?: string;
  username?: string;
  password?: string;
  roles?: string;
  dateAssigned?: Moment;
}

export class Users implements IUsers {
  constructor(
    public id?: string,
    public userID?: string,
    public username?: string,
    public password?: string,
    public roles?: string,
    public dateAssigned?: Moment
  ) {}
}
