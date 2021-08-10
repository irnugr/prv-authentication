package id.co.microservice.prv_authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import id.co.microservice.prv_authentication.entity.UsersEntity;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Long> {
	
	@Query(
			nativeQuery = true,
			value = "select id, username, password, email, firstname, lastname, createdate as createDate, "
				  + "createby as createBy, updatedate as updateDate, updateby as updateBy "
				  + "from users where username = :username "
		)
	UsersEntity getUsersByUsername(@Param("username") String username);

}
