package cn.itcast.bos.dao.city;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.city.City;

public interface CityDao extends JpaRepository<City, Integer>,
		JpaSpecificationExecutor<City> {
	@Query("from City where pid=?1")
	List<City> findAll(int pid);

}
