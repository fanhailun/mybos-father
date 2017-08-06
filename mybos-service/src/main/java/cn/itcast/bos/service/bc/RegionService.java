package cn.itcast.bos.service.bc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.bc.Region;

public interface RegionService {

	public void save(List<Region> list);

	public Page<Region> pageQuery(PageRequest pagerequest);

	public Region findById(String id);

	public Region findByPostcode(String postcode);

	public void save(Region model);

	public Page<Region> pageQuery(PageRequest pagerequest,
			Specification<Region> spec);

	public List<Region> findAll();

	public List<Region> findAllByPcd(String s);

	public Region importpcd(String rid);

	public String pageFromRedis(PageRequest pagerequest,
			Specification<Region> spec);

}
