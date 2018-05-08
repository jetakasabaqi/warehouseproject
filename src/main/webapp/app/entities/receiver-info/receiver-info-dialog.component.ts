import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ReceiverInfo } from './receiver-info.model';
import { ReceiverInfoPopupService } from './receiver-info-popup.service';
import { ReceiverInfoService } from './receiver-info.service';

@Component({
    selector: 'jhi-receiver-info-dialog',
    templateUrl: './receiver-info-dialog.component.html'
})
export class ReceiverInfoDialogComponent implements OnInit {

    receiverInfo: ReceiverInfo;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private receiverInfoService: ReceiverInfoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.receiverInfo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.receiverInfoService.update(this.receiverInfo));
        } else {
            this.subscribeToSaveResponse(
                this.receiverInfoService.create(this.receiverInfo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ReceiverInfo>>) {
        result.subscribe((res: HttpResponse<ReceiverInfo>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ReceiverInfo) {
        this.eventManager.broadcast({ name: 'receiverInfoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-receiver-info-popup',
    template: ''
})
export class ReceiverInfoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private receiverInfoPopupService: ReceiverInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.receiverInfoPopupService
                    .open(ReceiverInfoDialogComponent as Component, params['id']);
            } else {
                this.receiverInfoPopupService
                    .open(ReceiverInfoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
