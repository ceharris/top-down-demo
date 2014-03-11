package ceh.demo.facelets;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ceh.demo.Contact;
import ceh.demo.service.AddContactException;
import ceh.demo.service.AddContactService;

@Named
@RequestScoped
public class AddContactBean {

  static final String SUCCESS_OUTCOME_ID = "success";
  
  @Inject
  protected AddContactService service;
  
  private Contact contact;

  @PostConstruct
  public void init() {
    contact = service.newContact();
  }
  
  public Contact getContact() {
    return contact;
  }

  void setContact(Contact contact) {
    this.contact = contact;
  }

  public String save() {
    try {
      service.saveContact(contact);
      return SUCCESS_OUTCOME_ID;
    }
    catch (AddContactException ex) {
      return null;
    }
  }

}
