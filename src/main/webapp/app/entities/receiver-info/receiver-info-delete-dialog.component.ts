import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ReceiverInfo } from './receiver-info.model';
import { ReceiverInfoPopupService } from './receiver-info-popup.service';
import { ReceiverInfoService } from './receiver-info.service';

@Component({
    selector: 'jhi-receiver-info-delete-dialog',
    templateUrl: './receiver-info-delete-dialog.component.html'
})
export class ReceiverInfoDeleteDialogComponent {

    receiverInfo: ReceiverInfo;

    constructor(
        private receiverInfoService: ReceiverInfoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.receiverInfoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'receiverInfoListModification',
                content: 'Deleted an receiverInfo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-receiver-info-delete-popup',
    template: ''
})
export class ReceiverInfoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private receiverInfoPopupService: ReceiverInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.receiverInfoPopupService
                .open(ReceiverInfoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
