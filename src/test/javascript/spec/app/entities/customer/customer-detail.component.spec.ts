import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { MAgentsTestModule } from '../../../test.module';
import { CustomerDetailComponent } from 'app/entities/customer/customer-detail.component';
import { Customer } from 'app/shared/model/customer.model';

describe('Component Tests', () => {
  describe('Customer Management Detail Component', () => {
    let comp: CustomerDetailComponent;
    let fixture: ComponentFixture<CustomerDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ customer: new Customer('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MAgentsTestModule],
        declarations: [CustomerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CustomerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustomerDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load customer on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.customer).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
