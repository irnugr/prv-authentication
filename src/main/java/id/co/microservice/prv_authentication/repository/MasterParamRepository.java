package id.co.microservice.prv_authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import id.co.microservice.prv_authentication.entity.MasterParamEntity;

@Repository
public interface MasterParamRepository extends JpaRepository<MasterParamEntity, Long> {
	
	@Query(
			nativeQuery = true,
			value = "select id, paramCode, paramValue, paramDesc, createDate, createBy "
				  + "from masterparam where paramcode = :paramCode "
	)
	MasterParamEntity getParamByParamCode(@Param("paramCode") String paramCode);

}
