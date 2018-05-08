import { BaseEntity } from './../../shared';

export class Status implements BaseEntity {
    constructor(
        public id?: number,
        public statusName?: string,
    ) {
    }
}
