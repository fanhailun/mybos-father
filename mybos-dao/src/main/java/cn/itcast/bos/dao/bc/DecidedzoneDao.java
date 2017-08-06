package cn.itcast.bos.dao.bc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.bc.DecidedZone;

public interface DecidedzoneDao extends JpaRepository<DecidedZone, String>,
		JpaSpecificationExecutor<DecidedZone> {
	@Query("from DecidedZone where id=?1")
	public DecidedZone findById(String id);

}
