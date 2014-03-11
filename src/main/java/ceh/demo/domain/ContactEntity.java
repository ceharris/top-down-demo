package ceh.demo.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import ceh.demo.Contact;

@Entity
@Table(name = "contact")
public class ContactEntity implements Contact {

  @Id
  @GeneratedValue
  private Long id;
  
  @Version
  private Long version;
  
  @Column(name = "last_name", nullable = false, unique = true)
  private String lastName;
  
  public Serializable getId() {
    return id;
  }
  
  @Override
  public String getLastName() {
    return lastName;
  }

  @Override
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

}
