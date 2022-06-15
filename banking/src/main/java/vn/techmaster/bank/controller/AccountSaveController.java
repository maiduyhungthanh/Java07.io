package vn.techmaster.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.techmaster.bank.service.BankService;

@RestController
@RequestMapping("/api")
public class AccountSaveController {
    @Autowired BankService bankService;

    @GetMapping("/accountsave")
    public ResponseEntity<?> AccountSave() {
      return ResponseEntity.ok(bankService.getAllAccountSaves());
    } 
}
