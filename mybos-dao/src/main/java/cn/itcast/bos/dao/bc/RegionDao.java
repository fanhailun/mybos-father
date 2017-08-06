package cn.itcast.bos.dao.bc;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.bc.Region;

public interface RegionDao extends JpaRepository<Region, String>,
		JpaSpecificationExecutor<Region> {
	@Query("from Region where id=?1")
	public Region findById(String id);

	@Query("from Region where postcode=?1")
	public Region findByPostcode(String postcode);

	@Query("from Region where province like ?1 or city like ?1 or district like ?1")
	public List<Region> findAllByPcd(String s);

	@Query("From Region where id=?1")
	public Region importpcd(String rid);

	@Query("from Region where province=?1 and city=?2 and district=?3")
	public Region findRegionByProvinceAndCityAndDistrict(String province,
			String city, String district);

}
