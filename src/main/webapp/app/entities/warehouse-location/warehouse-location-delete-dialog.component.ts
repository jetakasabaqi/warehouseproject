import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { WarehouseLocation } from './warehouse-location.model';
import { WarehouseLocationPopupService } from './warehouse-location-popup.service';
import { WarehouseLocationService } from './warehouse-location.service';

@Component({
    selector: 'jhi-warehouse-location-delete-dialog',
    templateUrl: './warehouse-location-delete-dialog.component.html'
})
export class WarehouseLocationDeleteDialogComponent {

    warehouseLocation: WarehouseLocation;

    constructor(
        private warehouseLocationService: WarehouseLocationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.warehouseLocationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'warehouseLocationListModification',
                content: 'Deleted an warehouseLocation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-warehouse-location-delete-popup',
    template: ''
})
export class WarehouseLocationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private warehouseLocationPopupService: WarehouseLocationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.warehouseLocationPopupService
                .open(WarehouseLocationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
