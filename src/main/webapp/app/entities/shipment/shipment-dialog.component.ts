import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Shipment } from './shipment.model';
import { ShipmentPopupService } from './shipment-popup.service';
import { ShipmentService } from './shipment.service';
import { Person, PersonService } from '../person';
import { Vendor, VendorService } from '../vendor';
import { ReceiverInfo, ReceiverInfoService } from '../receiver-info';
import { Employee, EmployeeService } from '../employee';
import { Status, StatusService } from '../status';
import { Product, ProductService } from '../product';
import { WarehouseLocation, WarehouseLocationService } from '../warehouse-location';

@Component({
    selector: 'jhi-shipment-dialog',
    templateUrl: './shipment-dialog.component.html'
})
export class ShipmentDialogComponent implements OnInit {

    shipment: Shipment;
    isSaving: boolean;

    senderps: Person[];

    sendervs: Vendor[];

    receivers: ReceiverInfo[];

    employees: Employee[];

    statuses: Status[];

    products: Product[];

    locations: WarehouseLocation[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private shipmentService: ShipmentService,
        private personService: PersonService,
        private vendorService: VendorService,
        private receiverInfoService: ReceiverInfoService,
        private employeeService: EmployeeService,
        private statusService: StatusService,
        private productService: ProductService,
        private warehouseLocationService: WarehouseLocationService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.personService
            .query({filter: 'shipment-is-null'})
            .subscribe((res: HttpResponse<Person[]>) => {
                if (!this.shipment.senderP || !this.shipment.senderP.id) {
                    this.senderps = res.body;
                } else {
                    this.personService
                        .find(this.shipment.senderP.id)
                        .subscribe((subRes: HttpResponse<Person>) => {
                            this.senderps = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.vendorService
            .query({filter: 'shipment-is-null'})
            .subscribe((res: HttpResponse<Vendor[]>) => {
                if (!this.shipment.senderV || !this.shipment.senderV.id) {
                    this.sendervs = res.body;
                } else {
                    this.vendorService
                        .find(this.shipment.senderV.id)
                        .subscribe((subRes: HttpResponse<Vendor>) => {
                            this.sendervs = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.receiverInfoService
            .query({filter: 'shipment-is-null'})
            .subscribe((res: HttpResponse<ReceiverInfo[]>) => {
                if (!this.shipment.receiver || !this.shipment.receiver.id) {
                    this.receivers = res.body;
                } else {
                    this.receiverInfoService
                        .find(this.shipment.receiver.id)
                        .subscribe((subRes: HttpResponse<ReceiverInfo>) => {
                            this.receivers = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.employeeService
            .query({filter: 'shipment-is-null'})
            .subscribe((res: HttpResponse<Employee[]>) => {
                if (!this.shipment.employee || !this.shipment.employee.id) {
                    this.employees = res.body;
                } else {
                    this.employeeService
                        .find(this.shipment.employee.id)
                        .subscribe((subRes: HttpResponse<Employee>) => {
                            this.employees = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.statusService
            .query({filter: 'shipment-is-null'})
            .subscribe((res: HttpResponse<Status[]>) => {
                if (!this.shipment.status || !this.shipment.status.id) {
                    this.statuses = res.body;
                } else {
                    this.statusService
                        .find(this.shipment.status.id)
                        .subscribe((subRes: HttpResponse<Status>) => {
                            this.statuses = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.productService
            .query({filter: 'shipment-is-null'})
            .subscribe((res: HttpResponse<Product[]>) => {
                if (!this.shipment.product || !this.shipment.product.id) {
                    this.products = res.body;
                } else {
                    this.productService
                        .find(this.shipment.product.id)
                        .subscribe((subRes: HttpResponse<Product>) => {
                            this.products = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.warehouseLocationService
            .query({filter: 'shipment-is-null'})
            .subscribe((res: HttpResponse<WarehouseLocation[]>) => {
                if (!this.shipment.location || !this.shipment.location.id) {
                    this.locations = res.body;
                } else {
                    this.warehouseLocationService
                        .find(this.shipment.location.id)
                        .subscribe((subRes: HttpResponse<WarehouseLocation>) => {
                            this.locations = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.shipment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.shipmentService.update(this.shipment));
        } else {
            this.subscribeToSaveResponse(
                this.shipmentService.create(this.shipment));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Shipment>>) {
        result.subscribe((res: HttpResponse<Shipment>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Shipment) {
        this.eventManager.broadcast({ name: 'shipmentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPersonById(index: number, item: Person) {
        return item.id;
    }

    trackVendorById(index: number, item: Vendor) {
        return item.id;
    }

    trackReceiverInfoById(index: number, item: ReceiverInfo) {
        return item.id;
    }

    trackEmployeeById(index: number, item: Employee) {
        return item.id;
    }

    trackStatusById(index: number, item: Status) {
        return item.id;
    }

    trackProductById(index: number, item: Product) {
        return item.id;
    }

    trackWarehouseLocationById(index: number, item: WarehouseLocation) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-shipment-popup',
    template: ''
})
export class ShipmentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shipmentPopupService: ShipmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.shipmentPopupService
                    .open(ShipmentDialogComponent as Component, params['id']);
            } else {
                this.shipmentPopupService
                    .open(ShipmentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
