/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { Jeta123TestModule } from '../../../test.module';
import { ReceiverInfoDetailComponent } from '../../../../../../main/webapp/app/entities/receiver-info/receiver-info-detail.component';
import { ReceiverInfoService } from '../../../../../../main/webapp/app/entities/receiver-info/receiver-info.service';
import { ReceiverInfo } from '../../../../../../main/webapp/app/entities/receiver-info/receiver-info.model';

describe('Component Tests', () => {

    describe('ReceiverInfo Management Detail Component', () => {
        let comp: ReceiverInfoDetailComponent;
        let fixture: ComponentFixture<ReceiverInfoDetailComponent>;
        let service: ReceiverInfoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Jeta123TestModule],
                declarations: [ReceiverInfoDetailComponent],
                providers: [
                    ReceiverInfoService
                ]
            })
            .overrideTemplate(ReceiverInfoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReceiverInfoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReceiverInfoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ReceiverInfo(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.receiverInfo).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
