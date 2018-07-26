/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.check.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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
import org.wxjs.les.common.utils.DateUtils;
import org.wxjs.les.common.web.BaseController;
import org.wxjs.les.modules.check.entity.Tsitecheck;
import org.wxjs.les.modules.check.export.TsitecheckExport;
import org.wxjs.les.modules.check.service.TsitecheckService;
import org.wxjs.les.modules.exam.entity.Siterecord;


/**
 * 现场踏勘Controller
 * @author 千里目
 * @version 2018-07-10
 */
@Controller
@RequestMapping(value = "${adminPath}/check/tsitecheck")
public class TsitecheckController extends BaseController {

	@Autowired
	private TsitecheckService tsitecheckService;
	
	
	
	@ModelAttribute
	public Tsitecheck get(@RequestParam(required=false) String id) {
		Tsitecheck entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tsitecheckService.get(id);
		}
		if (entity == null){
			entity = new Tsitecheck();
		}
		return entity;
	}
	
	@RequiresPermissions("check:tsitecheck:view")
	@RequestMapping(value = {"list", ""})
	public String list(Tsitecheck tsitecheck, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Tsitecheck> page = tsitecheckService.findPage(new Page<Tsitecheck>(request, response), tsitecheck); 
		model.addAttribute("page", page);
		return "modules/check/tsitecheckList";
	}

	@RequiresPermissions("check:tsitecheck:view")
	@RequestMapping(value = "form")
	public String form(Tsitecheck tsitecheck, Model model) {
		model.addAttribute("tsitecheck", tsitecheck);
		return "modules/check/tsitecheckForm";
	}

	@RequiresPermissions("check:tsitecheck:edit")
	@RequestMapping(value = "save")
	public String save(Tsitecheck tsitecheck, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tsitecheck)){
			return form(tsitecheck, model);
		}
		tsitecheckService.save(tsitecheck);
		addMessage(redirectAttributes, "保存现场踏勘信息成功");
		
		return "redirect:"+Global.getAdminPath()+"/check/tsitecheck/?repage";
	}
	
	
	@RequiresPermissions("check:tsitecheck:edit")
	@RequestMapping(value = "saveInfo")
	public String saveInfo(Tsitecheck tsitecheck, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tsitecheck)){
		return form(tsitecheck, model);
		}
		tsitecheckService.saveInfo(tsitecheck);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/check/tsitecheck/form?id="+tsitecheck.getId();
	}
										
	@RequiresPermissions("check:tsitecheck:edit")
	@RequestMapping(value = "delete")
	public String delete(Tsitecheck tsitecheck, RedirectAttributes redirectAttributes) {
		tsitecheckService.delete(tsitecheck);
		addMessage(redirectAttributes, "删除现场踏勘信息成功");
		return "redirect:"+Global.getAdminPath()+"/check/tsitecheck/?repage";
	}
	
	@RequiresPermissions("check:tsitecheck:view")
	@RequestMapping(value = "exportPDF")
	public String exportPDF(Tsitecheck tsitecheck, HttpServletResponse response,  Model model, RedirectAttributes redirectAttributes) {
		Tsitecheck check = tsitecheckService.get(tsitecheck.getId());
		model.addAttribute("tsitecheck", check);
		try {
            String fileName = "现场踏勘"+DateUtils.getDate("yyyyMMddHHmmss")+".pdf";
            TsitecheckExport export = new TsitecheckExport(check);
            export.write(response, fileName);
    		return null;
		} catch (Exception e) {
			logger.error("导出失败", e);
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}		
		return "modules/check/tsitecheckForm";
	}
	
	
}