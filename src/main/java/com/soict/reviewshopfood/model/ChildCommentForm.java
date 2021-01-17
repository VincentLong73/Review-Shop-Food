package com.soict.reviewshopfood.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildCommentForm {
    private String reply;
    private int commentParentId;
}
