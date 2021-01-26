package org.mockito.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.medhub.user.User;
import org.medhub.user.UserRepository;
import org.medhub.user.UserService;

public class TestUserService {
	@InjectMocks
	UserService userService;

	@Mock
	UserRepository repo;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getUserByIdTest() {
		when(repo.findUserById("1")).thenReturn(new User("1", "Luke", "123456", "admin"));

		User usr = userService.getUserByid("1");

		assertEquals("1", usr.getid());
		assertEquals("123456", usr.getPassword());
		assertEquals("admin", usr.getAccountType());
	}

	@Test
	public void getAllUsersTest() {
		List<User> list = new ArrayList<User>();
		User userOne = new User("1", "Luke", "pass1", "admin");
		User userTwo = new User("2", "Evan", "pass2", "patient");
		User userThree = new User("3", "Aaron", "pass3", "doctor");

		list.add(userOne);
		list.add(userTwo);
		list.add(userThree);

		when(repo.findAll()).thenReturn(list);

		List<User> userList = userService.getAllUsers();

		assertEquals(3, userList.size());
		verify(repo, times(1)).findAll();
	}
	
	@Test
	public void getAttributeByTypeTest() {
		when(repo.findUserById("3")).thenReturn(new User("3", "Aaron", "pass3", "doctor"));
		
		assertEquals("pass3",userService.getUserAttribute("3", "password"));
		assertEquals("Aaron",userService.getUserAttribute("3", "username"));
		assertEquals("doctor",userService.getUserAttribute("3", "accountType"));
		assertEquals("3",userService.getUserAttribute("3", "id"));
	}
	
	@Test
	public void getAllDoctorsTest() {
		List<String> list = new ArrayList<String>();
		list.add("aaron");
		list.add("drphil");

		when(repo.findAllDoctorUsernames("doctor")).thenReturn(list);

		List<String> doctorNames = userService.findAllDoctorUsernames("doctor");
		
		assertEquals(2, doctorNames.size());
		assertEquals("aaron", doctorNames.get(0));
		assertEquals("drphil", doctorNames.get(1));
	}
}
