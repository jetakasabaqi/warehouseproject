import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Jeta123SharedModule } from '../../shared';
import {
    ReceiverInfoService,
    ReceiverInfoPopupService,
    ReceiverInfoComponent,
    ReceiverInfoDetailComponent,
    ReceiverInfoDialogComponent,
    ReceiverInfoPopupComponent,
    ReceiverInfoDeletePopupComponent,
    ReceiverInfoDeleteDialogComponent,
    receiverInfoRoute,
    receiverInfoPopupRoute,
    ReceiverInfoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...receiverInfoRoute,
    ...receiverInfoPopupRoute,
];

@NgModule({
    imports: [
        Jeta123SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ReceiverInfoComponent,
        ReceiverInfoDetailComponent,
        ReceiverInfoDialogComponent,
        ReceiverInfoDeleteDialogComponent,
        ReceiverInfoPopupComponent,
        ReceiverInfoDeletePopupComponent,
    ],
    entryComponents: [
        ReceiverInfoComponent,
        ReceiverInfoDialogComponent,
        ReceiverInfoPopupComponent,
        ReceiverInfoDeleteDialogComponent,
        ReceiverInfoDeletePopupComponent,
    ],
    providers: [
        ReceiverInfoService,
        ReceiverInfoPopupService,
        ReceiverInfoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Jeta123ReceiverInfoModule {}
