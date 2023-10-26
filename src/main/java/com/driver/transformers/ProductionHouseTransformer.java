package com.driver.transformers;

import com.driver.EntryDto.ProductionHouseEntryDto;
import com.driver.model.ProductionHouse;

import java.util.ArrayList;

public class ProductionHouseTransformer {
    public static ProductionHouse productionHouseEntryDTOToProductionHouse(ProductionHouseEntryDto productionHouseEntryDto){
        ProductionHouse productionHouse=new ProductionHouse();
        productionHouse.setName(productionHouseEntryDto.getName());
        productionHouse.setRatings(0);
        productionHouse.setWebSeriesList(new ArrayList<>());
        return productionHouse;
    }
}
