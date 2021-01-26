package org.medhub.contact;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ContactRepository extends CrudRepository<Contact, String> {
	public List<Contact> findByUserId(String userId);

	public Contact findContactById(String id);
}
