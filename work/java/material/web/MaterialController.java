/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.material.web;

import java.util.Calendar;

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
import org.wxjs.les.common.utils.DateUtils;
import org.wxjs.les.common.utils.StringUtils;
import org.wxjs.les.modules.base.entity.Signature;
import org.wxjs.les.modules.exam.entity.Siterecord;
import org.wxjs.les.modules.material.entity.Material;
import org.wxjs.les.modules.material.export.MaterialExport;
import org.wxjs.les.modules.material.service.MaterialService;

/**
 * 视听资料证据Controller
 * @author 千里目
 * @version 2018-07-23
 */
@Controller
@RequestMapping(value = "${adminPath}/material/material")
public class MaterialController extends BaseController {

	@Autowired
	private MaterialService materialService;
	
	@ModelAttribute
	public Material get(@RequestParam(required=false) String id) {
		Material entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = materialService.get(id);
		}
		if (entity == null){
			entity = new Material();
		}
		return entity;
	}
	
	@RequiresPermissions("material:material:view")
	@RequestMapping(value = {"list", ""})
	public String list(Material material, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Material> page = materialService.findPage(new Page<Material>(request, response), material); 
		model.addAttribute("page", page);
		return "modules/material/materialList";
	}

	@RequiresPermissions("material:material:view")
	@RequestMapping(value = "form")
	public String form(Material material, Model model) {
		if(material.getIsNewRecord()){
			//tcase.setCaseStage("10");
			//tcase.setCaseStageStatus("0");
			material.setMaterialComment("拍摄时间："+"2018年7月25日"+"\n"
			+"拍摄地点："+"无锡"+"\n"+"拍摄内容："+"房屋筑建"+"\n"+"拍摄张数："+"第一张"
			+"\n"+"拍摄人："+"张晓宇");
		}
		model.addAttribute("material", material);
		return "modules/material/materialForm";
	}

	@RequiresPermissions("material:material:edit")
	@RequestMapping(value = "save")
	public String save(Material material, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, material)){
			return form(material, model);
		}
		materialService.save(material);
		addMessage(redirectAttributes, "保存视听资料证据成功");
		return "redirect:"+Global.getAdminPath()+"/material/material/?repage";
	}
	
	@RequiresPermissions("material:material:edit")
	@RequestMapping(value = "saveInfo")
	public String saveInfo(Material material, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, material)){
			return form(material, model);
		}
		materialService.saveInfo(material);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/material/material/form?id="+material.getId();
	}
	
	
	@RequiresPermissions("material:material:edit")
	@RequestMapping(value = "delete")
	public String delete(Material material, RedirectAttributes redirectAttributes) {
		materialService.delete(material);
		addMessage(redirectAttributes, "删除视听资料证据成功");
		return "redirect:"+Global.getAdminPath()+"/material/material/?repage";
	}
	
	@RequiresPermissions("material:material:view")
	@RequestMapping(value = "exportPDF")
	public String exportPDF(Material material, HttpServletResponse response,  Model model, RedirectAttributes redirectAttributes) {
		Material ma = materialService.get(material.getId());
		model.addAttribute("material", ma);
		try {
            String fileName = "视听资料"+DateUtils.getDate("yyyyMMddHHmmss")+".pdf";
            MaterialExport export = new MaterialExport(ma);
            export.write(response, fileName);
    		return null;
		} catch (Exception e) {
			logger.error("导出失败", e);
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}		
		return "modules/material/materialForm";
	}
	
	
	
}