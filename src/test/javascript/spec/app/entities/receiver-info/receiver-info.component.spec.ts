/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Jeta123TestModule } from '../../../test.module';
import { ReceiverInfoComponent } from '../../../../../../main/webapp/app/entities/receiver-info/receiver-info.component';
import { ReceiverInfoService } from '../../../../../../main/webapp/app/entities/receiver-info/receiver-info.service';
import { ReceiverInfo } from '../../../../../../main/webapp/app/entities/receiver-info/receiver-info.model';

describe('Component Tests', () => {

    describe('ReceiverInfo Management Component', () => {
        let comp: ReceiverInfoComponent;
        let fixture: ComponentFixture<ReceiverInfoComponent>;
        let service: ReceiverInfoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Jeta123TestModule],
                declarations: [ReceiverInfoComponent],
                providers: [
                    ReceiverInfoService
                ]
            })
            .overrideTemplate(ReceiverInfoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReceiverInfoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReceiverInfoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ReceiverInfo(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.receiverInfos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
