import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUsers } from 'app/shared/model/users.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { UsersService } from './users.service';
import { UsersDeleteDialogComponent } from './users-delete-dialog.component';

@Component({
  selector: 'jhi-users',
  templateUrl: './users.component.html',
})
export class UsersComponent implements OnInit, OnDestroy {
  users: IUsers[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected usersService: UsersService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.users = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.usersService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IUsers[]>) => this.paginateUsers(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.users = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUsers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUsers): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUsers(): void {
    this.eventSubscriber = this.eventManager.subscribe('usersListModification', () => this.reset());
  }

  delete(users: IUsers): void {
    const modalRef = this.modalService.open(UsersDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.users = users;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateUsers(data: IUsers[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.users.push(data[i]);
      }
    }
  }
}
