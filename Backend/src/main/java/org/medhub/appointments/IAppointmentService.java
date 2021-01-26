package org.medhub.appointments;

import java.util.List;

public interface IAppointmentService {

	List<String> findLocationById(String userId);
}
