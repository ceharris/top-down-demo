package ceh.demo.facelets;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.jmock.Expectations.returnValue;
import static org.jmock.Expectations.throwException;
import static org.junit.Assert.assertThat;

import org.jmock.Expectations;
import org.jmock.api.Action;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import ceh.demo.Contact;
import ceh.demo.service.AddContactException;
import ceh.demo.service.AddContactService;

public class AddContactBeanTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();
  
  @Mock
  private AddContactService service;
  
  @Mock
  private Contact contact;
  
  private AddContactBean bean = new AddContactBean();
  
  @Before
  public void setUp() {
    bean.service = service;
  }
  
  @Test
  public void testInit() throws Exception {
    context.checking(new Expectations() { { 
      oneOf(service).newContact();
      will(returnValue(contact));
    } });
    
    bean.init();
    assertThat(bean.getContact(), is(sameInstance(contact)));
  }
  
  @Test
  public void testSaveSuccess() throws Exception {
    context.checking(saveContactExpectations(returnValue(null)));
    bean.setContact(contact);
    assertThat(bean.save(), is(equalTo(AddContactBean.SUCCESS_OUTCOME_ID)));
  }

  @Test
  public void testSaveFailure() throws Exception {
    context.checking(saveContactExpectations(throwException(new AddContactException())));
    bean.setContact(contact);
    assertThat(bean.save(), is(nullValue()));
  }
  
  private Expectations saveContactExpectations(final Action outcome) 
      throws Exception {
    return new Expectations() { {
      oneOf(service).saveContact(with(same(contact)));
      will(outcome);
    } };
  }

}
