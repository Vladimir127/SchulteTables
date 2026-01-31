package com.github.vladimir127.schultetables.domain.repository;

public class Extra {
    private boolean withVariants;
    private boolean ascending;

    public Extra(boolean withVariants, boolean ascending) {
        this.withVariants = withVariants;
        this.ascending = ascending;
    }

    public boolean isWithVariants() {
        return withVariants;
    }

    public boolean isAscending() {
        return ascending;
    }
}
