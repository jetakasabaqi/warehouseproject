/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { Jeta123TestModule } from '../../../test.module';
import { WarehouseLocationDetailComponent } from '../../../../../../main/webapp/app/entities/warehouse-location/warehouse-location-detail.component';
import { WarehouseLocationService } from '../../../../../../main/webapp/app/entities/warehouse-location/warehouse-location.service';
import { WarehouseLocation } from '../../../../../../main/webapp/app/entities/warehouse-location/warehouse-location.model';

describe('Component Tests', () => {

    describe('WarehouseLocation Management Detail Component', () => {
        let comp: WarehouseLocationDetailComponent;
        let fixture: ComponentFixture<WarehouseLocationDetailComponent>;
        let service: WarehouseLocationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Jeta123TestModule],
                declarations: [WarehouseLocationDetailComponent],
                providers: [
                    WarehouseLocationService
                ]
            })
            .overrideTemplate(WarehouseLocationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WarehouseLocationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WarehouseLocationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new WarehouseLocation(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.warehouseLocation).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
