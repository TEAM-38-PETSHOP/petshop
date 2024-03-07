package org.globaroman.petshopba.dto.ordercart;

import lombok.Data;
import org.globaroman.petshopba.model.cartorder.Status;

@Data
public class OrderStatusDto {
    private Status status;
}
