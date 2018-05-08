import { BaseEntity } from './../../shared';

export class Price implements BaseEntity {
    constructor(
        public id?: number,
        public price?: number,
    ) {
    }
}
