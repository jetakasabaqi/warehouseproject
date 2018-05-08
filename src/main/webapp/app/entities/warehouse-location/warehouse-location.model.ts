import { BaseEntity } from './../../shared';

export class WarehouseLocation implements BaseEntity {
    constructor(
        public id?: number,
        public address?: string,
        public country?: string,
        public city?: BaseEntity,
    ) {
    }
}
