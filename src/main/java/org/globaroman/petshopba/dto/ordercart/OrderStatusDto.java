package org.globaroman.petshopba.dto.ordercart;

import lombok.Data;
import org.globaroman.petshopba.model.user.Status;

@Data
public class OrderStatusDto {
    private Status status;
}
