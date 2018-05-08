import { BaseEntity } from './../../shared';

export class Shipment implements BaseEntity {
    constructor(
        public id?: number,
        public senderP?: BaseEntity,
        public senderV?: BaseEntity,
        public receiver?: BaseEntity,
        public employee?: BaseEntity,
        public status?: BaseEntity,
        public product?: BaseEntity,
        public location?: BaseEntity,
    ) {
    }
}
