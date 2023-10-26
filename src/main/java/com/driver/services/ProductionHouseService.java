package com.driver.services;


import com.driver.EntryDto.ProductionHouseEntryDto;
import com.driver.model.ProductionHouse;
import com.driver.repository.ProductionHouseRepository;
import com.driver.transformers.ProductionHouseTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductionHouseService {

    @Autowired
    ProductionHouseRepository productionHouseRepository;

    public Integer addProductionHouseToDb(ProductionHouseEntryDto productionHouseEntryDto){

        ProductionHouse productionHouse=ProductionHouseTransformer.productionHouseEntryDTOToProductionHouse(productionHouseEntryDto);
        productionHouseRepository.save(productionHouse);
        return productionHouse.getId();
    }



}
