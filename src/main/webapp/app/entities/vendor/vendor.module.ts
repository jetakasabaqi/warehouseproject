import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Jeta123SharedModule } from '../../shared';
import {
    VendorService,
    VendorPopupService,
    VendorComponent,
    VendorDetailComponent,
    VendorDialogComponent,
    VendorPopupComponent,
    VendorDeletePopupComponent,
    VendorDeleteDialogComponent,
    vendorRoute,
    vendorPopupRoute,
    VendorResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...vendorRoute,
    ...vendorPopupRoute,
];

@NgModule({
    imports: [
        Jeta123SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        VendorComponent,
        VendorDetailComponent,
        VendorDialogComponent,
        VendorDeleteDialogComponent,
        VendorPopupComponent,
        VendorDeletePopupComponent,
    ],
    entryComponents: [
        VendorComponent,
        VendorDialogComponent,
        VendorPopupComponent,
        VendorDeleteDialogComponent,
        VendorDeletePopupComponent,
    ],
    providers: [
        VendorService,
        VendorPopupService,
        VendorResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Jeta123VendorModule {}
