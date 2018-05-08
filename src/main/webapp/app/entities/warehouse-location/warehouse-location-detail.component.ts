import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { WarehouseLocation } from './warehouse-location.model';
import { WarehouseLocationService } from './warehouse-location.service';

@Component({
    selector: 'jhi-warehouse-location-detail',
    templateUrl: './warehouse-location-detail.component.html'
})
export class WarehouseLocationDetailComponent implements OnInit, OnDestroy {

    warehouseLocation: WarehouseLocation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private warehouseLocationService: WarehouseLocationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInWarehouseLocations();
    }

    load(id) {
        this.warehouseLocationService.find(id)
            .subscribe((warehouseLocationResponse: HttpResponse<WarehouseLocation>) => {
                this.warehouseLocation = warehouseLocationResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInWarehouseLocations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'warehouseLocationListModification',
            (response) => this.load(this.warehouseLocation.id)
        );
    }
}
