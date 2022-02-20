package com.blps.firstlaboratory.services;

import com.blps.firstlaboratory.model.Shipping;
import com.blps.firstlaboratory.repostitory.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Map<String, Boolean> checkExists(String products) {
        Map<String, Boolean> result = new HashMap<>();

        Arrays.stream(products.split(" ")).distinct().forEach(productName ->
                result.put(productName, productRepository.existsByProductName(productName)));

        return result;
    }


    public Map<String, Boolean> checkPossibility(String products, String country, String region) {
        Map<String, Boolean> result = new HashMap<>();
        Map<String, List<Shipping>> shippingList = new HashMap<>();

        Arrays.stream(products.split(" ")).distinct().forEach(productName ->
                shippingList.put(productName, productRepository.findByProductName(productName).getShippingList())
        );

        shippingList.forEach((productName, list) -> list.forEach(x -> {
            if (x.getCountry().equals(country) && x.getRegion().equals(region)) {
                result.put(productName, true);
            } else {
                result.put(productName, false);
            }
        }));

        return result;
    }
}