package ceh.demo.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import ceh.demo.Contact;
import ceh.demo.ContactFactory;
import ceh.demo.repository.ContactRepository;

@ApplicationScoped
public class AddContactServiceBean implements AddContactService {

  @Inject
  protected ContactFactory contactFactory;

  @Inject
  protected ContactRepository repository;

  @Override
  public Contact newContact() {
    return contactFactory.newContact();
  }

  @Override
  @Transactional
  public void saveContact(Contact contact) throws AddContactException {
    try {
      repository.add(contact);
    }
    catch (PersistenceException ex) {
      throw new AddContactException();
    }
  }

}
