package vn.techmaster.bank.request;

public record WithDrawRequest(String userId, String accountId, Long amount) {
  
}

