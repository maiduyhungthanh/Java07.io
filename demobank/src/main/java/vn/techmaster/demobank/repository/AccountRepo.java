package vn.techmaster.demobank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.demobank.model.Account;


@Repository
public interface AccountRepo extends JpaRepository<Account,Long> {
    
}
