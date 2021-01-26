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
import org.medhub.appointments.Appointment;
import org.medhub.appointments.AppointmentRepository;
import org.medhub.appointments.AppointmentService;
import org.medhub.user.User;
import org.medhub.user.UserRepository;
import org.medhub.user.UserService;

public class TestAppointmentService {
	@InjectMocks
	AppointmentService appointmentService;

	@Mock
	AppointmentRepository repo;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void findAppointmentByIdTest() {
		List<Appointment> list = new ArrayList<Appointment>();
		Appointment ap1 = new Appointment("1", "1223", "123", "01-19-2011", "sunset ln", "5:40");
		Appointment ap2 = new Appointment("2", "1223", "123", "01-20-2011", "sunset av", "5:41");
		Appointment ap3 = new Appointment("3", "32", "323", "01-21-2011", "sunset st", "5:42");

		list.add(ap1);
		list.add(ap2);
		list.add(ap3);

		when(repo.findAppointmentById("32")).thenReturn(ap3);

		Appointment ap4 = appointmentService.getAppointment("32");

		assertEquals(ap3, ap4);
	}
}


