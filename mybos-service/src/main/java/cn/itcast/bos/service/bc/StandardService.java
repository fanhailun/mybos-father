package cn.itcast.bos.service.bc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import cn.itcast.bos.domain.bc.Standard;

public interface StandardService {

	public void save(Standard model);

	public Page<Standard> pageQuery(PageRequest pagerequest);

	public void updatedeltag(String id);

	public List<Standard> findByDeltag();

}
