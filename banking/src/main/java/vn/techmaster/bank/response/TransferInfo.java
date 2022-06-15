package vn.techmaster.bank.response;

public record TransferInfo(String accountid_send, String bank_name_send, Long amount_send,String accountid_receive, String bank_name_receive, Long amount_receive) {
    
}
