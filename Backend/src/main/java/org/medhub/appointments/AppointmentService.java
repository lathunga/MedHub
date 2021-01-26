package org.medhub.appointments;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class AppointmentService implements IAppointmentService{
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	public List<Appointment> getAllAppointments(String userId) {
		List<Appointment> appointments = new ArrayList<Appointment>();
		appointmentRepository.findByUserId(userId).forEach(appointments::add);
		return appointments;
	}
	
	public Appointment getAppointment(String id) {
		return appointmentRepository.findAppointmentById(id);
	}
	
	public void addAppointment(Appointment appointment) {
		appointmentRepository.save(appointment);
	}
	
	public void updateAppointment(Appointment appointment) {
		appointmentRepository.save(appointment);
	}
	
	public void deleteAppointment(String id) {
		appointmentRepository.deleteById(id);
	}
	
	@Override
	public List<String> findLocationById(String userId) {
		return (List<String>) appointmentRepository.findLocationById(userId);
	}
	
	public List<String> getCorAppointments(String otherUserId) {
		return (List<String>) appointmentRepository.getCorAppointments(otherUserId);
	}
}
