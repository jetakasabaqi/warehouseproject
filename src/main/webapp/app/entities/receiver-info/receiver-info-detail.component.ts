import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ReceiverInfo } from './receiver-info.model';
import { ReceiverInfoService } from './receiver-info.service';

@Component({
    selector: 'jhi-receiver-info-detail',
    templateUrl: './receiver-info-detail.component.html'
})
export class ReceiverInfoDetailComponent implements OnInit, OnDestroy {

    receiverInfo: ReceiverInfo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private receiverInfoService: ReceiverInfoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInReceiverInfos();
    }

    load(id) {
        this.receiverInfoService.find(id)
            .subscribe((receiverInfoResponse: HttpResponse<ReceiverInfo>) => {
                this.receiverInfo = receiverInfoResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInReceiverInfos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'receiverInfoListModification',
            (response) => this.load(this.receiverInfo.id)
        );
    }
}
