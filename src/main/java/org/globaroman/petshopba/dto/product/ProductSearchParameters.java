package org.globaroman.petshopba.dto.product;

public record ProductSearchParameters(String[] name,
                                      String[] productNameId,
                                      String[] brand,
                                      String[] group,
                                      String[] type,
                                      String[] breedSize,
                                      String[] countryProduct,
                                      String [] animals,
                                      String [] categories) {

}
