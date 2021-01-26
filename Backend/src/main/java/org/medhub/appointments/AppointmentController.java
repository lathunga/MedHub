package org.medhub.appointments;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.medhub.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppointmentController {
	
	@Autowired
	private AppointmentService appointmentService;
	
//	@RequestMapping("/users/{id}/appointments")
//	public List<Appointment> getAllAppointments(@PathVariable String id) {
//		return appointmentService.getAllAppointments(id);
//	}
	
	@RequestMapping("/users/gets/{userId}/appointments")
	public List<String> getAllAppointmentsInfo(@PathVariable String userId) {
		return appointmentService.findLocationById(userId);
	}
	
	@RequestMapping("/users/get/{otherUserId}/appointments")
	public List<String> getCorAppointments(@PathVariable String otherUserId) {
		return appointmentService.getCorAppointments(otherUserId);
	}
	
	@RequestMapping("/users/{userId}/appointments/{id}")
	public Appointment getAppointment(@PathVariable String id) {
		return appointmentService.getAppointment(id);
	}
	
	@PostMapping(value = "/users/{userId}/appointments")
	public void addAppointment(@RequestParam Map<String,String> body) {
		Appointment appointment = new Appointment(UUID.randomUUID().toString(), body.get("userId"),
				body.get("otherUserId"), body.get("datetime"), body.get("location"), body.get("time"));
		appointmentService.addAppointment(appointment);
	}
	
	@PutMapping(value = "/users/{userId}/appointments/{id}")
	public void updateAppointment(@RequestParam Map<String,String> body, @PathVariable String id, @PathVariable String userId) {
		Appointment appointment = getAppointment(id);
		appointment.setUser(new User(userId, "","",""));
		appointmentService.updateAppointment(appointment);
	}
	
	@DeleteMapping(value = "/users/{userId}/appointments/{id}")
	public void deleteAppointment(@PathVariable String id) {
		appointmentService.deleteAppointment(id);
	}
}
