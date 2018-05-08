/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { Jeta123TestModule } from '../../../test.module';
import { ShipmentDialogComponent } from '../../../../../../main/webapp/app/entities/shipment/shipment-dialog.component';
import { ShipmentService } from '../../../../../../main/webapp/app/entities/shipment/shipment.service';
import { Shipment } from '../../../../../../main/webapp/app/entities/shipment/shipment.model';
import { PersonService } from '../../../../../../main/webapp/app/entities/person';
import { VendorService } from '../../../../../../main/webapp/app/entities/vendor';
import { ReceiverInfoService } from '../../../../../../main/webapp/app/entities/receiver-info';
import { EmployeeService } from '../../../../../../main/webapp/app/entities/employee';
import { StatusService } from '../../../../../../main/webapp/app/entities/status';
import { ProductService } from '../../../../../../main/webapp/app/entities/product';
import { WarehouseLocationService } from '../../../../../../main/webapp/app/entities/warehouse-location';

describe('Component Tests', () => {

    describe('Shipment Management Dialog Component', () => {
        let comp: ShipmentDialogComponent;
        let fixture: ComponentFixture<ShipmentDialogComponent>;
        let service: ShipmentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Jeta123TestModule],
                declarations: [ShipmentDialogComponent],
                providers: [
                    PersonService,
                    VendorService,
                    ReceiverInfoService,
                    EmployeeService,
                    StatusService,
                    ProductService,
                    WarehouseLocationService,
                    ShipmentService
                ]
            })
            .overrideTemplate(ShipmentDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShipmentDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShipmentService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Shipment(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.shipment = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'shipmentListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Shipment();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.shipment = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'shipmentListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
