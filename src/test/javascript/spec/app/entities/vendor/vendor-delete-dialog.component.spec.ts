/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { Jeta123TestModule } from '../../../test.module';
import { VendorDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/vendor/vendor-delete-dialog.component';
import { VendorService } from '../../../../../../main/webapp/app/entities/vendor/vendor.service';

describe('Component Tests', () => {

    describe('Vendor Management Delete Component', () => {
        let comp: VendorDeleteDialogComponent;
        let fixture: ComponentFixture<VendorDeleteDialogComponent>;
        let service: VendorService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Jeta123TestModule],
                declarations: [VendorDeleteDialogComponent],
                providers: [
                    VendorService
                ]
            })
            .overrideTemplate(VendorDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VendorDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VendorService);
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
