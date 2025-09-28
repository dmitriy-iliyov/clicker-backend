package com.example.clicker.user.models.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record PagedDataDto<T>(
        List<T> data,
        Long total) {

   public static <T> PagedDataDto<T> toPagedDataDto(Page<T> page) {
       return new PagedDataDto<>(page.getContent(), page.getTotalElements());
   }

    public static <T> PagedDataDto<T> toPagedDataDto(List<T> data, Long total) {
        return new PagedDataDto<>(data, total);
    }
}
