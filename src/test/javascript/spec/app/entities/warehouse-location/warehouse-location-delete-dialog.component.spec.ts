/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { Jeta123TestModule } from '../../../test.module';
import { WarehouseLocationDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/warehouse-location/warehouse-location-delete-dialog.component';
import { WarehouseLocationService } from '../../../../../../main/webapp/app/entities/warehouse-location/warehouse-location.service';

describe('Component Tests', () => {

    describe('WarehouseLocation Management Delete Component', () => {
        let comp: WarehouseLocationDeleteDialogComponent;
        let fixture: ComponentFixture<WarehouseLocationDeleteDialogComponent>;
        let service: WarehouseLocationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Jeta123TestModule],
                declarations: [WarehouseLocationDeleteDialogComponent],
                providers: [
                    WarehouseLocationService
                ]
            })
            .overrideTemplate(WarehouseLocationDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WarehouseLocationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WarehouseLocationService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
