package com.thienhoa.blog.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PaginationResponse<T> {

    private List<T> contents;

    private Integer total;

    private Integer totalPage;

    private Integer amount;

    private Integer page;

    private Boolean isFirst = false;

    private Boolean isLast = false;

    public PaginationResponse(Integer total,Integer amount, Integer page, List<T> contents){

        this.contents =  contents.stream().skip((page - 1)*amount).limit(amount).collect(Collectors.toList());

        this.total = total;
        this.amount = amount;
        this.page = page;

        //Calculation total page
        if(total % amount == 0){
            this.totalPage = total / amount;
        } else {
            this.totalPage = total / amount + 1;
        }

        if(page == 1){
            this.isFirst = true;
        }

        if(page >= this.totalPage){
            this.isLast = true;
        }

    }
}
