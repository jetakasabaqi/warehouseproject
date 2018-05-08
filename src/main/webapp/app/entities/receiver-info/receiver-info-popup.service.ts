import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { ReceiverInfo } from './receiver-info.model';
import { ReceiverInfoService } from './receiver-info.service';

@Injectable()
export class ReceiverInfoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private receiverInfoService: ReceiverInfoService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.receiverInfoService.find(id)
                    .subscribe((receiverInfoResponse: HttpResponse<ReceiverInfo>) => {
                        const receiverInfo: ReceiverInfo = receiverInfoResponse.body;
                        this.ngbModalRef = this.receiverInfoModalRef(component, receiverInfo);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.receiverInfoModalRef(component, new ReceiverInfo());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    receiverInfoModalRef(component: Component, receiverInfo: ReceiverInfo): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.receiverInfo = receiverInfo;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
