import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { WarehouseLocation } from './warehouse-location.model';
import { WarehouseLocationPopupService } from './warehouse-location-popup.service';
import { WarehouseLocationService } from './warehouse-location.service';
import { City, CityService } from '../city';

@Component({
    selector: 'jhi-warehouse-location-dialog',
    templateUrl: './warehouse-location-dialog.component.html'
})
export class WarehouseLocationDialogComponent implements OnInit {

    warehouseLocation: WarehouseLocation;
    isSaving: boolean;

    cities: City[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private warehouseLocationService: WarehouseLocationService,
        private cityService: CityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.cityService
            .query({filter: 'warehouselocation-is-null'})
            .subscribe((res: HttpResponse<City[]>) => {
                if (!this.warehouseLocation.city || !this.warehouseLocation.city.id) {
                    this.cities = res.body;
                } else {
                    this.cityService
                        .find(this.warehouseLocation.city.id)
                        .subscribe((subRes: HttpResponse<City>) => {
                            this.cities = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.warehouseLocation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.warehouseLocationService.update(this.warehouseLocation));
        } else {
            this.subscribeToSaveResponse(
                this.warehouseLocationService.create(this.warehouseLocation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<WarehouseLocation>>) {
        result.subscribe((res: HttpResponse<WarehouseLocation>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: WarehouseLocation) {
        this.eventManager.broadcast({ name: 'warehouseLocationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCityById(index: number, item: City) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-warehouse-location-popup',
    template: ''
})
export class WarehouseLocationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private warehouseLocationPopupService: WarehouseLocationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.warehouseLocationPopupService
                    .open(WarehouseLocationDialogComponent as Component, params['id']);
            } else {
                this.warehouseLocationPopupService
                    .open(WarehouseLocationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
