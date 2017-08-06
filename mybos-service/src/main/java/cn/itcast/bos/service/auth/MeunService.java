package cn.itcast.bos.service.auth;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import cn.itcast.bos.domain.auth.Menu;

public interface MeunService {

	public List<Menu> ajaxListHasSonMenus();

	public Page<Menu> pageQuery(PageRequest pagerequest);

	public void save(Menu model);

	public List<Menu> ajaxList();

	public List<Menu> findMenuByUserId(Integer id);

}
