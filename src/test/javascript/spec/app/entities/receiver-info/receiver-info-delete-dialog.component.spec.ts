/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { Jeta123TestModule } from '../../../test.module';
import { ReceiverInfoDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/receiver-info/receiver-info-delete-dialog.component';
import { ReceiverInfoService } from '../../../../../../main/webapp/app/entities/receiver-info/receiver-info.service';

describe('Component Tests', () => {

    describe('ReceiverInfo Management Delete Component', () => {
        let comp: ReceiverInfoDeleteDialogComponent;
        let fixture: ComponentFixture<ReceiverInfoDeleteDialogComponent>;
        let service: ReceiverInfoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Jeta123TestModule],
                declarations: [ReceiverInfoDeleteDialogComponent],
                providers: [
                    ReceiverInfoService
                ]
            })
            .overrideTemplate(ReceiverInfoDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReceiverInfoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReceiverInfoService);
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
