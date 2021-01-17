package com.soict.reviewshopfood.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormNewFood {
    private int id;
    private String content;
    private String shortDescription;
    private String name;
    private float price;
    private List<String> imageUrl;
}
