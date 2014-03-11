package ceh.demo.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.apache.commons.lang.Validate;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ceh.demo.Contact;
import ceh.demo.domain.ContactEntity;

@RunWith(Arquillian.class)
public class JpaContactRepositoryIT {

  @Deployment
  public static Archive<?> createDeployment() {
      return ShrinkWrap.create(WebArchive.class)
          .addPackage(Contact.class.getPackage())
          .addPackage(ContactEntity.class.getPackage())
          .addClasses(ContactRepository.class, JpaContactRepository.class)
          .addClasses(Validate.class)
          .addAsResource("persistence-test.xml", "META-INF/persistence.xml")
          .addAsResource("META-INF/orm.xml", "META-INF/orm.xml")
          .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
  }
  
  @Inject
  private ContactRepository repository;
  
  @PersistenceContext
  private EntityManager entityManager;
  
  @Inject
  private UserTransaction tx;
  
  @Before
  public void setUp() throws Exception {
    tx.begin();
    entityManager.joinTransaction();
  }
  
  @After
  public void tearDown() throws Exception {
    tx.rollback();
  }
  
  @Test
  public void testAddSuccess() throws Exception {    
    ContactEntity contact = new ContactEntity();
    contact.setLastName("Smith");
    repository.add(contact);
    entityManager.flush();
    entityManager.clear();
    
    ContactEntity actual = entityManager.find(ContactEntity.class, 
        contact.getId());
    
    assertThat(actual, is(not(nullValue())));
    assertThat(actual, is(not(sameInstance(contact))));
    assertThat(actual.getLastName(), is(equalTo(contact.getLastName())));
  }

}
