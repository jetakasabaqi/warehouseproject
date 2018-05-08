import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { WarehouseLocationComponent } from './warehouse-location.component';
import { WarehouseLocationDetailComponent } from './warehouse-location-detail.component';
import { WarehouseLocationPopupComponent } from './warehouse-location-dialog.component';
import { WarehouseLocationDeletePopupComponent } from './warehouse-location-delete-dialog.component';

@Injectable()
export class WarehouseLocationResolvePagingParams implements Resolve<any> {

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

export const warehouseLocationRoute: Routes = [
    {
        path: 'warehouse-location',
        component: WarehouseLocationComponent,
        resolve: {
            'pagingParams': WarehouseLocationResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WarehouseLocations'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'warehouse-location/:id',
        component: WarehouseLocationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WarehouseLocations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const warehouseLocationPopupRoute: Routes = [
    {
        path: 'warehouse-location-new',
        component: WarehouseLocationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WarehouseLocations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'warehouse-location/:id/edit',
        component: WarehouseLocationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WarehouseLocations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'warehouse-location/:id/delete',
        component: WarehouseLocationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WarehouseLocations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
