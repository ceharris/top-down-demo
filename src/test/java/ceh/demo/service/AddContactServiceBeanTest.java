package ceh.demo.service;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.jmock.Expectations.returnValue;
import static org.jmock.Expectations.throwException;
import static org.junit.Assert.assertThat;

import javax.persistence.PersistenceException;

import org.jmock.Expectations;
import org.jmock.api.Action;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import ceh.demo.Contact;
import ceh.demo.ContactFactory;
import ceh.demo.repository.ContactRepository;

public class AddContactServiceBeanTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();
  
  @Mock
  private ContactRepository repository;
  
  @Mock
  private ContactFactory contactFactory;
  
  @Mock
  private Contact contact;
  
  private AddContactServiceBean bean = new AddContactServiceBean();
  
  @Before
  public void setUp() throws Exception {
    bean.repository = repository;
    bean.contactFactory = contactFactory;
  }
  
  @Test
  public void testNewContact() throws Exception {
    context.checking(new Expectations() { { 
      oneOf(contactFactory).newContact();
      returnValue(contact);
    } });
    
    assertThat(bean.newContact(), is(not(nullValue())));
  }

  @Test
  public void testSaveContactSuccess() throws Exception {
    context.checking(contactRepositoryExpectations(returnValue(null)));
    bean.saveContact(contact);
  }

  @Test(expected = AddContactException.class)
  public void testSaveContactWhenLastNameIsDuplicate() throws Exception {
    context.checking(contactRepositoryExpectations(
        throwException(new PersistenceException())));
    bean.saveContact(contact);
  }

  private Expectations contactRepositoryExpectations(final Action outcome) {
    return new Expectations() { {
      oneOf(repository).add(with(same(contact)));
      will(outcome);
    } };
  }

}
