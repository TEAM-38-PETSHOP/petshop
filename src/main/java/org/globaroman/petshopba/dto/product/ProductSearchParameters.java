package org.globaroman.petshopba.dto.product;

public record ProductSearchParameters(String[] name,
                                      String[] brand,
                                      String[] price,
                                      String[] group,
                                      String[] type,
                                      String[] breedSize,
                                      String[] packaging,
                                      String[] countryProduct) {

}
