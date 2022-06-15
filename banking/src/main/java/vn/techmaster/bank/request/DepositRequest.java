package vn.techmaster.bank.request;

public record DepositRequest(String userId, String accountId, Long amount) {
  
}
