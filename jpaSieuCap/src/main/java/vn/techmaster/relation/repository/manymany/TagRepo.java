package vn.techmaster.relation.repository.manymany;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.techmaster.relation.model.manymany.noextracolumns.Tag;

public interface TagRepo extends JpaRepository<Tag, Long> {
  
}
