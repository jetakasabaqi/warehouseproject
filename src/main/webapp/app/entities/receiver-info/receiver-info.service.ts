import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { ReceiverInfo } from './receiver-info.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ReceiverInfo>;

@Injectable()
export class ReceiverInfoService {

    private resourceUrl =  SERVER_API_URL + 'api/receiver-infos';

    constructor(private http: HttpClient) { }

    create(receiverInfo: ReceiverInfo): Observable<EntityResponseType> {
        const copy = this.convert(receiverInfo);
        return this.http.post<ReceiverInfo>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(receiverInfo: ReceiverInfo): Observable<EntityResponseType> {
        const copy = this.convert(receiverInfo);
        return this.http.put<ReceiverInfo>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ReceiverInfo>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ReceiverInfo[]>> {
        const options = createRequestOption(req);
        return this.http.get<ReceiverInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ReceiverInfo[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ReceiverInfo = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ReceiverInfo[]>): HttpResponse<ReceiverInfo[]> {
        const jsonResponse: ReceiverInfo[] = res.body;
        const body: ReceiverInfo[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ReceiverInfo.
     */
    private convertItemFromServer(receiverInfo: ReceiverInfo): ReceiverInfo {
        const copy: ReceiverInfo = Object.assign({}, receiverInfo);
        return copy;
    }

    /**
     * Convert a ReceiverInfo to a JSON which can be sent to the server.
     */
    private convert(receiverInfo: ReceiverInfo): ReceiverInfo {
        const copy: ReceiverInfo = Object.assign({}, receiverInfo);
        return copy;
    }
}
