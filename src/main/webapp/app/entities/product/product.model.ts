import { BaseEntity } from './../../shared';

export class Product implements BaseEntity {
    constructor(
        public id?: number,
        public price?: BaseEntity,
    ) {
    }
}
