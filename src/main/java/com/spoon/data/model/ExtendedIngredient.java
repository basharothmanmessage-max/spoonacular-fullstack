package com.spoon.data.model;

import lombok.Data;

/**
 *
 * @author User
 */
@Data
public class ExtendedIngredient {

    private Long id;
    private String name;
    private String original;
    private Double amount;
    private String unit;
}
