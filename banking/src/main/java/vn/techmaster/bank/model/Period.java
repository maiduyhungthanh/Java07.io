package vn.techmaster.bank.model;

public enum Period {
    MONTH_of_0("Không kỳ hạn"),
    MONTH_of_1("Một Tháng"),
    MONTH_of_3("Ba Tháng"),
    MONTH_of_6("Sáu Tháng"),
    MONTH_of_12("Mười Hai Tháng");
    
    public final String label;
    private Period(String label) {
        this.label = label;
    }
}
