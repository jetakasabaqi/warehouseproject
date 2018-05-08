import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Jeta123SharedModule } from '../../shared';
import {
    WarehouseLocationService,
    WarehouseLocationPopupService,
    WarehouseLocationComponent,
    WarehouseLocationDetailComponent,
    WarehouseLocationDialogComponent,
    WarehouseLocationPopupComponent,
    WarehouseLocationDeletePopupComponent,
    WarehouseLocationDeleteDialogComponent,
    warehouseLocationRoute,
    warehouseLocationPopupRoute,
    WarehouseLocationResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...warehouseLocationRoute,
    ...warehouseLocationPopupRoute,
];

@NgModule({
    imports: [
        Jeta123SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        WarehouseLocationComponent,
        WarehouseLocationDetailComponent,
        WarehouseLocationDialogComponent,
        WarehouseLocationDeleteDialogComponent,
        WarehouseLocationPopupComponent,
        WarehouseLocationDeletePopupComponent,
    ],
    entryComponents: [
        WarehouseLocationComponent,
        WarehouseLocationDialogComponent,
        WarehouseLocationPopupComponent,
        WarehouseLocationDeleteDialogComponent,
        WarehouseLocationDeletePopupComponent,
    ],
    providers: [
        WarehouseLocationService,
        WarehouseLocationPopupService,
        WarehouseLocationResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Jeta123WarehouseLocationModule {}
