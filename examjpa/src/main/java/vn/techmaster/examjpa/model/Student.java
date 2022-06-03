package vn.techmaster.examjpa.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="entity_student")
@Table(name = "table_student")
public class Student {
    @Id
    int id;
    String name;

    
}
