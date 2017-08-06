package cn.itcast.bos.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.bos.service.auth.FunctionService;
import cn.itcast.bos.service.auth.MeunService;
import cn.itcast.bos.service.auth.RoleService;
import cn.itcast.bos.service.bc.DecidedzoneService;
import cn.itcast.bos.service.bc.RegionService;
import cn.itcast.bos.service.bc.StaffService;
import cn.itcast.bos.service.bc.StandardService;
import cn.itcast.bos.service.bc.SubareaService;
import cn.itcast.bos.service.city.CityService;
import cn.itcast.bos.service.qp.NoticebillService;
import cn.itcast.bos.service.user.UserService;

@Service
public class FacadeService {

	@Autowired
	private UserService userService;

	@Autowired
	private StandardService standardService;

	@Autowired
	private StaffService staffService;

	@Autowired
	private RegionService regionService;

	@Autowired
	private DecidedzoneService decidedzoneService;

	@Autowired
	private SubareaService subareaService;

	@Autowired
	private CityService cityService;

	@Autowired
	private NoticebillService noticebillService;

	@Autowired
	private FunctionService functionService;

	@Autowired
	private MeunService meunService;

	@Autowired
	private RoleService roleService;

	public UserService getUserService() {
		return userService;
	}

	public StandardService getStandardService() {
		return standardService;
	}

	public StaffService getStaffService() {
		return staffService;
	}

	public RegionService getRegionService() {
		return regionService;
	}

	public SubareaService getSubareaService() {
		return subareaService;
	}

	public DecidedzoneService getDecidedzoneService() {
		return decidedzoneService;
	}

	public CityService getCityService() {

		return cityService;
	}

	public NoticebillService getNoticebillService() {

		return noticebillService;
	}

	public FunctionService getFunctionService() {

		return functionService;
	}

	public MeunService getMeunService() {

		return meunService;
	}

	public RoleService getRoleService() {
		return roleService;
	}
}
