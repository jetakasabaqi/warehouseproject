import { BaseEntity } from './../../shared';

export class ReceiverInfo implements BaseEntity {
    constructor(
        public id?: number,
        public fullName?: string,
        public address?: string,
        public zipCode?: string,
    ) {
    }
}
