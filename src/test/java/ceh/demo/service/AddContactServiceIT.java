package ceh.demo.service;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import ceh.demo.Contact;
import ceh.demo.domain.ContactEntity;
import ceh.demo.repository.ContactRepository;

@RunWith(Arquillian.class)
public class AddContactServiceIT {

  @Deployment
  public static Archive<?> createDeployment() {
    WebArchive archive = ShrinkWrap.create(WebArchive.class)
        .addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml")
            .importRuntimeAndTestDependencies().resolve().withTransitivity().asFile())
        .addPackage(Contact.class.getPackage())
        .addPackage(ContactEntity.class.getPackage())
        .addPackage(ContactRepository.class.getPackage())
        .addPackage(AddContactService.class.getPackage())
        .addAsResource("persistence-test.xml", "META-INF/persistence.xml")
        .addAsResource("META-INF/orm.xml", "META-INF/orm.xml")
        .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    System.out.println(archive.toString(true));
    return archive;
  }
  
  @Inject
  private AddContactService service;
  
  @Test
  public void testSaveContactSuccess() throws Exception {
    Contact contact = service.newContact();
    contact.setLastName("Jones");
    service.saveContact(contact);
  }
  
  @Test(expected = AddContactException.class)
  public void testSaveSameContactTwice() throws Exception {
    Contact contact = service.newContact();
    contact.setLastName("Smith");
    service.saveContact(contact);
    service.saveContact(contact);
  }
  
}
