package com.soict.reviewshopfood.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormEditPassword {
    private String password;
    private String newPassword;
    private String rePassword;
}
