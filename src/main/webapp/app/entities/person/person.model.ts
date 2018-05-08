import { BaseEntity } from './../../shared';

export class Person implements BaseEntity {
    constructor(
        public id?: number,
        public fullName?: string,
        public tel?: string,
        public address?: string,
        public zipCode?: string,
        public email?: string,
    ) {
    }
}
