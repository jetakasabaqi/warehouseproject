/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Jeta123TestModule } from '../../../test.module';
import { WarehouseLocationComponent } from '../../../../../../main/webapp/app/entities/warehouse-location/warehouse-location.component';
import { WarehouseLocationService } from '../../../../../../main/webapp/app/entities/warehouse-location/warehouse-location.service';
import { WarehouseLocation } from '../../../../../../main/webapp/app/entities/warehouse-location/warehouse-location.model';

describe('Component Tests', () => {

    describe('WarehouseLocation Management Component', () => {
        let comp: WarehouseLocationComponent;
        let fixture: ComponentFixture<WarehouseLocationComponent>;
        let service: WarehouseLocationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Jeta123TestModule],
                declarations: [WarehouseLocationComponent],
                providers: [
                    WarehouseLocationService
                ]
            })
            .overrideTemplate(WarehouseLocationComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WarehouseLocationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WarehouseLocationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new WarehouseLocation(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.warehouseLocations[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
