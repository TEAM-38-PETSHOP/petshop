package org.globaroman.petshopba.dto.ordercart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;
import org.globaroman.petshopba.model.cartorder.Address;
import org.globaroman.petshopba.model.cartorder.Status;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseOrderDto implements Serializable {
    private Long id;
    private Long userId;
    private Status status;
    private BigDecimal total;
    private LocalDateTime orderDate;
    private Address address;
    private Set<ResponseOrderItemDto> orderItems;
}
