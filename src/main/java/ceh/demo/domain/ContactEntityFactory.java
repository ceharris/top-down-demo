package ceh.demo.domain;

import javax.enterprise.context.ApplicationScoped;

import ceh.demo.Contact;
import ceh.demo.ContactFactory;

@ApplicationScoped
public class ContactEntityFactory implements ContactFactory {

  @Override
  public Contact newContact() {
    return new ContactEntity();
  }

}
