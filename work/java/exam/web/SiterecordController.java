/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.exam.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.web.BaseController;
import org.wxjs.les.modules.exam.entity.Siterecord;
import org.wxjs.les.modules.exam.export.SiterecordExport;
import org.wxjs.les.modules.exam.service.SiterecordService;
import org.wxjs.les.common.utils.DateUtils;
import org.wxjs.les.common.utils.StringUtils;

/**
 * 现场检查笔录Controller
 * @author 千里目
 * @version 
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/siterecord")
public class SiterecordController extends BaseController {

	@Autowired
	private SiterecordService siterecordService;
	
	@ModelAttribute
	public Siterecord get(@RequestParam(required=false) String id) {
		Siterecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = siterecordService.get(id);
		}
		if (entity == null){
			entity = new Siterecord();
		}
		return entity;
	}
	
	@RequiresPermissions("exam:siterecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(Siterecord siterecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Siterecord> page = siterecordService.findPage(new Page<Siterecord>(request, response), siterecord); 
		model.addAttribute("page", page);
		return "modules/exam/siterecordList";
	}

	@RequiresPermissions("exam:siterecord:view")
	@RequestMapping(value = "form")
	public String form(Siterecord siterecord, Model model) {
		model.addAttribute("siterecord", siterecord);
		return "modules/exam/siterecordForm";
	}

	@RequiresPermissions("exam:siterecord:edit")
	@RequestMapping(value = "save")
	public String save(Siterecord siterecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, siterecord)){
			return form(siterecord, model);
		}
		siterecordService.save(siterecord);
		addMessage(redirectAttributes, "保存现场检查笔录成功");
		return "redirect:"+Global.getAdminPath()+"/exam/siterecord/?repage";
	}
	
	@RequiresPermissions("exam:siterecord:edit")
	@RequestMapping(value = "saveInfo")
	public String saveInfo(Siterecord siterecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, siterecord)){
			return form(siterecord, model);
		}
		siterecordService.saveInfo(siterecord);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/exam/siterecord/form?id="+siterecord.getId();
	}
	
	
	@RequiresPermissions("exam:siterecord:edit")
	@RequestMapping(value = "delete")
	public String delete(Siterecord siterecord, RedirectAttributes redirectAttributes) {
		siterecordService.delete(siterecord);
		addMessage(redirectAttributes, "删除现场检查笔录成功");
		return "redirect:"+Global.getAdminPath()+"/exam/siterecord/?repage";
	}
	
	@RequiresPermissions("exam:siterecord:view")
	@RequestMapping(value = "exportPDF")
	public String exportPDF(Siterecord siterecord, HttpServletResponse response,  Model model, RedirectAttributes redirectAttributes) {
		Siterecord record = siterecordService.get(siterecord.getId());
		model.addAttribute("siterecord", record);
		try {
            String fileName = "现场检查"+DateUtils.getDate("yyyyMMddHHmmss")+".pdf";
            SiterecordExport export = new SiterecordExport(record);
            export.write(response, fileName);
    		return null;
		} catch (Exception e) {
			logger.error("导出失败", e);
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}		
		return "modules/exam/siterecordForm";
	}
	
	

}