package vn.techmaster.bank.request;

public record TransferRequest(String userId_send, String accountId_send,String userId_receive, String accountId_receive,Long amount) {
    
}
