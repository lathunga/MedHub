package org.mockito.tests;

import org.mockito.MockitoAnnotations;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.medhub.contact.ContactRepository;
import org.medhub.contact.ContactService;
import org.medhub.contact.Contact;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class TestContactService {
	@InjectMocks
	ContactService contactService;
	
	@Mock
	ContactRepository repo;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getContactByIdTest() {
		Contact con = new Contact("1", "2af", "3bg");
		when(repo.findContactById("1")).thenReturn(con);
		
		Contact contact = contactService.getContact("1");
		
		assertEquals("1", contact.getid());
		assertEquals("3bg", contact.getotherUserId());
		assertEquals("2af", contact.getUser().getid());
	}
	
	@Test
	public void getByUserIdTest() {
		List<Contact> list = new ArrayList<Contact>();
		Contact contactOne = new Contact("1", "2af", "3bg");
		Contact contactTwo = new Contact("2", "2af", "4ch");

		list.add(contactOne);
		list.add(contactTwo);
		
		when(repo.findByUserId("2af")).thenReturn(list);
		
		List<Contact> contacts = contactService.getAllContacts("2af");
		
		assertEquals("3bg", contacts.get(0).getotherUserId());
		assertEquals("4ch", contacts.get(1).getotherUserId());
	}
}
