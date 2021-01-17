package com.soict.reviewshopfood.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormEditFood {
    private int id;
    private String content;
    private String shortDescription;
    private String name;
    private float price;
    private List<String> imageUrl;
    private MultipartFile thumbnail;
    private MultipartFile[] foodImages;
}
