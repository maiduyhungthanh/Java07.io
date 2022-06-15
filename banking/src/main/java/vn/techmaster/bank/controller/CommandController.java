package vn.techmaster.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.techmaster.bank.request.DepositRequest;
import vn.techmaster.bank.request.TransferRequest;
import vn.techmaster.bank.request.WithDrawRequest;
import vn.techmaster.bank.request.WithDrawSaveRequest;
import vn.techmaster.bank.response.AccountInfo;
import vn.techmaster.bank.response.AccountSaveInfo;
import vn.techmaster.bank.response.TransferInfo;
import vn.techmaster.bank.service.BankService;

@RestController
@RequestMapping("/api")
public class CommandController {
  @Autowired
  BankService bankService;

  // Nap Tien
  @Operation(summary = "Nạp tiền")
  @PostMapping("/deposit")
  public ResponseEntity<AccountInfo> deposit(@RequestBody DepositRequest depositRequest) {
    return ResponseEntity.ok(bankService.deposit(depositRequest));
  }

  // Chuyển tiền
  @Operation(summary = "Chuyển tiền")
  @PostMapping("/transfer")
  public ResponseEntity<TransferInfo> transfer(@RequestBody TransferRequest transferRequest) {
    return ResponseEntity.ok(bankService.Transfer(transferRequest));
  }

  // Rut Tien TK THƯỜNG
  @Operation(summary = "Rút tiền TK THƯỜNG")
  @PostMapping("/withdraw")
  public ResponseEntity<AccountInfo> withDraw(@RequestBody WithDrawRequest withDrawRequest) {
    return ResponseEntity.ok(bankService.withDraw(withDrawRequest));
  }

   // Rut Tien TK TIẾT KIỆM
   @Operation(summary = "Rút tiền TK TIẾT KIỆM")
   @PostMapping("/withdrawsave")
   public ResponseEntity<AccountSaveInfo> withDrawSave(@RequestBody WithDrawSaveRequest withDrawSaveRequest) {
     return ResponseEntity.ok(bankService.withDrawSave(withDrawSaveRequest));
   }

}
