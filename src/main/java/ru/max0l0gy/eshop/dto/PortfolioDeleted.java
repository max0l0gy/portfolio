package ru.max0l0gy.eshop.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PortfolioDeleted {
        private boolean isDeleted;
}
