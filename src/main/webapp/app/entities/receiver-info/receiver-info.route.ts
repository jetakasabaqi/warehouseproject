import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ReceiverInfoComponent } from './receiver-info.component';
import { ReceiverInfoDetailComponent } from './receiver-info-detail.component';
import { ReceiverInfoPopupComponent } from './receiver-info-dialog.component';
import { ReceiverInfoDeletePopupComponent } from './receiver-info-delete-dialog.component';

@Injectable()
export class ReceiverInfoResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const receiverInfoRoute: Routes = [
    {
        path: 'receiver-info',
        component: ReceiverInfoComponent,
        resolve: {
            'pagingParams': ReceiverInfoResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReceiverInfos'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'receiver-info/:id',
        component: ReceiverInfoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReceiverInfos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const receiverInfoPopupRoute: Routes = [
    {
        path: 'receiver-info-new',
        component: ReceiverInfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReceiverInfos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'receiver-info/:id/edit',
        component: ReceiverInfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReceiverInfos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'receiver-info/:id/delete',
        component: ReceiverInfoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReceiverInfos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
