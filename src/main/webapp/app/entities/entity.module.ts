import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { Jeta123CityModule } from './city/city.module';
import { Jeta123PersonModule } from './person/person.module';
import { Jeta123EmployeeModule } from './employee/employee.module';
import { Jeta123PriceModule } from './price/price.module';
import { Jeta123ProductModule } from './product/product.module';
import { Jeta123StatusModule } from './status/status.module';
import { Jeta123VendorModule } from './vendor/vendor.module';
import { Jeta123ShipmentModule } from './shipment/shipment.module';
import { Jeta123ReceiverInfoModule } from './receiver-info/receiver-info.module';
import { Jeta123WarehouseLocationModule } from './warehouse-location/warehouse-location.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        Jeta123CityModule,
        Jeta123PersonModule,
        Jeta123EmployeeModule,
        Jeta123PriceModule,
        Jeta123ProductModule,
        Jeta123StatusModule,
        Jeta123VendorModule,
        Jeta123ShipmentModule,
        Jeta123ReceiverInfoModule,
        Jeta123WarehouseLocationModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Jeta123EntityModule {}
