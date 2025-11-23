package com.clicker.core.sgared;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageDto<T>(
        List<T> data,
        Long total) {

   public static <T> PageDto<T> of(Page<T> page) {
       return new PageDto<>(page.getContent(), page.getTotalElements());
   }

    public static <T> PageDto<T> of(List<T> data, Long total) {
        return new PageDto<>(data, total);
    }
}
