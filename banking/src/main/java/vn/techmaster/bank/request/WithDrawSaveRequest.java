package vn.techmaster.bank.request;

public record WithDrawSaveRequest(String userId, String accountSaveId, Long amount) {
    
}
