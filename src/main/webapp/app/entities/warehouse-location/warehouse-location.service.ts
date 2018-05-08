import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { WarehouseLocation } from './warehouse-location.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<WarehouseLocation>;

@Injectable()
export class WarehouseLocationService {

    private resourceUrl =  SERVER_API_URL + 'api/warehouse-locations';

    constructor(private http: HttpClient) { }

    create(warehouseLocation: WarehouseLocation): Observable<EntityResponseType> {
        const copy = this.convert(warehouseLocation);
        return this.http.post<WarehouseLocation>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(warehouseLocation: WarehouseLocation): Observable<EntityResponseType> {
        const copy = this.convert(warehouseLocation);
        return this.http.put<WarehouseLocation>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<WarehouseLocation>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<WarehouseLocation[]>> {
        const options = createRequestOption(req);
        return this.http.get<WarehouseLocation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<WarehouseLocation[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: WarehouseLocation = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<WarehouseLocation[]>): HttpResponse<WarehouseLocation[]> {
        const jsonResponse: WarehouseLocation[] = res.body;
        const body: WarehouseLocation[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to WarehouseLocation.
     */
    private convertItemFromServer(warehouseLocation: WarehouseLocation): WarehouseLocation {
        const copy: WarehouseLocation = Object.assign({}, warehouseLocation);
        return copy;
    }

    /**
     * Convert a WarehouseLocation to a JSON which can be sent to the server.
     */
    private convert(warehouseLocation: WarehouseLocation): WarehouseLocation {
        const copy: WarehouseLocation = Object.assign({}, warehouseLocation);
        return copy;
    }
}
