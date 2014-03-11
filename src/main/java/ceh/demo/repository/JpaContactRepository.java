package ceh.demo.repository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.Validate;

import ceh.demo.Contact;
import ceh.demo.domain.ContactEntity;

@ApplicationScoped
public class JpaContactRepository implements ContactRepository {

  @PersistenceContext
  protected EntityManager entityManager;
  
  @Override
  public void add(Contact contact) {
    Validate.isTrue(contact instanceof ContactEntity);
    entityManager.persist(contact);
  }

}
