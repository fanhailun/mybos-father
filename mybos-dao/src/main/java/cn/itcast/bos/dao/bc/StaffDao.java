package cn.itcast.bos.dao.bc;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.bc.Staff;

public interface StaffDao extends JpaRepository<Staff, String>,
		JpaSpecificationExecutor<Staff> {

	@Query("from Staff where telephone=?1")
	public Staff findByPhoneAjax(String telephone);

	@Modifying
	@Query("update Staff set deltag=0 where id=?1")
	public void updateDeltag(String id);

	@Query("from Staff where deltag=1")
	public List<Staff> findNameByAjax();

	@Query("from Staff where id=?1")
	public Staff importStaffName(String sid);

}
