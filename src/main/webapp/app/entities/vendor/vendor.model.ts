import { BaseEntity } from './../../shared';

export class Vendor implements BaseEntity {
    constructor(
        public id?: number,
        public fullName?: string,
        public address?: string,
        public email?: string,
        public website?: string,
        public contactPerson?: string,
        public zipCode?: string,
    ) {
    }
}
