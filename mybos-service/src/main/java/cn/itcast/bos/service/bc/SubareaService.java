package cn.itcast.bos.service.bc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.bc.Subarea;

public interface SubareaService {

	public void save(Subarea model);

	public Page<Subarea> querypage(PageRequest pagerequest);

	public Page<Subarea> querypage(PageRequest pagerequest,
			Specification<Subarea> spec);

	public List<Subarea> findAllByAjax(Specification<Subarea> specification);

	public List<Subarea> findNoAssociationSubarea();

	public List<Subarea> getAssociationSubarea(String did);

}
