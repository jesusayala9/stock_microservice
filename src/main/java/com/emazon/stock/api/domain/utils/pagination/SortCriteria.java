package com.emazon.stock.api.domain.utils.pagination;




public class SortCriteria {
    private String sortBy;
    private SortDirection direction;

    public SortCriteria(String sortBy, SortDirection direction) {
        this.sortBy = sortBy;
        this.direction = direction;
    }

    // Getters y Setters
    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public SortDirection getDirection() {
        return direction;
    }

    public void setDirection(SortDirection direction) {
        this.direction = direction;
    }

}
