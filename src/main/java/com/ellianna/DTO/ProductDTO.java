package com.ellianna.DTO;

import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ProductDTO {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class CreateProductDTO{
        public String name;
        public String description;
        public Long price;
        public Boolean available;
        @Null
        public Boolean isCustomized;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UpdateProductDTO{
        public String name;
        public String description;
        public Long price;
        public Boolean available;
        public Boolean isCustomized;
    }
}
