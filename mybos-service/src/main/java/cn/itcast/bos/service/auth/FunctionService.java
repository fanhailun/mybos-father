package cn.itcast.bos.service.auth;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import cn.itcast.bos.domain.auth.Function;

public interface FunctionService {

	public void save(Function model);

	public Page<Function> pageQuery(PageRequest pagerequest);

	public List<Function> findAll();

}
