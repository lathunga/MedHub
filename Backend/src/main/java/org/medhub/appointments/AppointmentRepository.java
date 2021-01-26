package org.medhub.appointments;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, String> {
	public List<Appointment> findByUserId(String userId);

	public Appointment findAppointmentById(String id);
	
	@Query(value = "SELECT a.datetime, a.time, u.username FROM appointments a, user u where a.user_id = :user_Id AND u.id = a.other_user_id", nativeQuery=true) 
    List<String> findLocationById(@Param("user_Id") String userId);
	
	@Query(value = "SELECT r.datetime, r.time FROM appointments r where r.other_user_id = :other_user_id", nativeQuery=true)
	List<String> getCorAppointments(@Param("other_user_id") String otherUserId);
}
