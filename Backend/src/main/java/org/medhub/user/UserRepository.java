package org.medhub.user;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface UserRepository extends CrudRepository<User, String> {

	@SuppressWarnings("unchecked")
	User save(User account);

	void delete(User account);

	public User findUserById(String id);
	
	public User findByUsername(String username);

	
	@Query(value = "SELECT r.username FROM user r where r.account_type = :account_type", nativeQuery=true) 
    List<String> findAllDoctorUsernames(@Param("account_type") String accountType);
	
	@Query(value = "Select r.id FROM user r where r.username = :username", nativeQuery=true)
	String getId(@Param("username") String username);
}
