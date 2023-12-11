package com.dom.solver.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopMessageDto {
    String shoppingSuccess;
    Long lives;
    Long gold;
    Long level;
    Long turn;
}
