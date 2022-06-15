package vn.techmaster.bank.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.bank.model.AccountSave;


@Repository
public interface AccountSaveRepo extends JpaRepository<AccountSave, String>{
  
}


