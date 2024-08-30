package com.emazon.stock.api.domain.utils.pagination;

import java.util.List;

public class PagedResult <T> {
    private  List<T> content;
    private  int totalElements;
    private  int totalPages;

    public PagedResult(List<T> content, int totalElements, int pageSize) {
        this.content = content;
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / pageSize);
    }

    public List<T> getContent() {
        return content;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
