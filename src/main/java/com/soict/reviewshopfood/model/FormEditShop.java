package com.soict.reviewshopfood.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormEditShop {
    private String phone;
    private String province;
    private String district;
    private String village;
    private String street;
    private String description;
}
