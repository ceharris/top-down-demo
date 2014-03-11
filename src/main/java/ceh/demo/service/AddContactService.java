package ceh.demo.service;

import ceh.demo.Contact;

public interface AddContactService {

  Contact newContact();
  
  void saveContact(Contact contact) throws AddContactException;
  
}
