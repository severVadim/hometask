package tests.api.test;

enum DateTimeMethods {

    PLUS_DAYS("plusDays"),
    MINUS_DAYS("minusDays"),
    PLUS_MONTHS("plusMonths"),
    MINUS_MONTHS("plusMonths"),
    PLUS_YEARS("plusYears"),
    MINUS_YEARS("plusYears");

    private String getMethod;

    DateTimeMethods(String getMethod) {
        this.getMethod = getMethod;
    }

    public String getGetMethod() {
        return getMethod;
    }
}
