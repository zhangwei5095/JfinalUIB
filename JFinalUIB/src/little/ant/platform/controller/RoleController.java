package little.ant.platform.controller;

import java.util.List;
import java.util.Map;

import little.ant.platform.annotation.Controller;
import little.ant.platform.model.Group;
import little.ant.platform.model.Role;
import little.ant.platform.service.RoleService;
import little.ant.platform.validator.RoleValidator;

import org.apache.log4j.Logger;

import com.jfinal.aop.Before;

@SuppressWarnings("unused")
@Controller(controllerKey = "/jf/platform/role")
public class RoleController extends BaseController {
	
	private static Logger log = Logger.getLogger(RoleController.class);
	
	private List<Group> noCheckedList;
	private List<Group> checkedList;
	
	private String moduleIds;
	private String operatorIds;
	
	public void index() {
		RoleService.service.list(splitPage);
		render("/platform/role/list.html");
	}
	
	@Before(RoleValidator.class)
	public void save() {
		ids = RoleService.service.save(getModel(Role.class));
		redirect("/jf/platform/role");
	}
	
	public void edit() {
		setAttr("role", Role.dao.findById(getPara()));
		render("/platform/role/update.html");
	}
	
	@Before(RoleValidator.class)
	public void update() {
		RoleService.service.update(getModel(Role.class));
		redirect("/jf/platform/role");
	}
	
	public void delete() {
		RoleService.service.delete(getPara());
		redirect("/jf/platform/role");
	}

	@SuppressWarnings("unchecked")
	public void select(){
		Map<String,Object> map = RoleService.service.select(ids);
		noCheckedList = (List<Group>) map.get("noCheckedList");
		checkedList = (List<Group>) map.get("checkedList");
		render("/platform/role/select.html");
	}
	
	public void getOperator(){
		Role role = Role.dao.findById(ids);
		renderJson(role);
	}

	public void setOperator(){
		RoleService.service.setOperator(ids, moduleIds, operatorIds);
		renderJson(ids);
	}
	
}


