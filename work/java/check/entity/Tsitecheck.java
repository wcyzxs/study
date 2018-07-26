/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.check.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Calendar;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import org.wxjs.les.common.persistence.DataEntity;
import org.wxjs.les.modules.base.entity.Signature;

/**
 * 现场踏勘Entity
 * @author 千里目
 * @version 2018-07-10
 */
public class Tsitecheck extends DataEntity<Tsitecheck> {
	
	private static final long serialVersionUID = 1L;
	private final String FORMAT_DATE = "yyyy-MM-dd";
	private String developOrg;		// 建设单位
	private String developContact;		// 建设单位联系人
	private String developPhone;		// 建设单位联系人电话
	private String constructionOrg;		// 施工单位
	private String constructionContact;		// 施工单位联系人
	private String constructionPhone;		// 施工单位联系人电话
	private String projectName;		// 工程名称
	private String projectAddress;		// 工程地址
	private String siteSituation;		// 现场检查工程情况
	private String sitePicture;		// 现场踏勘示意图
	private String siteCheckResult;		// 现场踏勘情况
	private String checker;		// 勘查人
	private Date checkDate;		// 勘查时间
	private Date beginDate;
	private Date endDate;
	
	private Signature checkerSig = new Signature(); // 勘察人签名
	private Signature partySig = new Signature();		// 当事人签名
	
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		//设置时间边界值，将时分秒设置为23：59：59，就能将当天的数据也能查出来。
		if(endDate != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endDate);
			calendar.set(Calendar.HOUR, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			endDate = calendar.getTime();
		}
		this.endDate = endDate;
	}

	public Tsitecheck() {
		super();
	}

	public Tsitecheck(String id){
		super(id);
	}

	@Length(min=1, max=100, message="建设单位长度必须介于 1 和 100 之间")
	public String getDevelopOrg() {
		return developOrg;
	}

	public void setDevelopOrg(String developOrg) {
		this.developOrg = developOrg;
	}
	
	@Length(min=1, max=32, message="建设单位联系人长度必须介于 1 和 32 之间")
	public String getDevelopContact() {
		return developContact;
	}

	public void setDevelopContact(String developContact) {
		this.developContact = developContact;
	}
	
	@Length(min=1, max=32, message="建设单位联系人电话长度必须介于 1 和 32 之间")
	public String getDevelopPhone() {
		return developPhone;
	}

	public void setDevelopPhone(String developPhone) {
		this.developPhone = developPhone;
	}
	
	@Length(min=1, max=100, message="施工单位长度必须介于 1 和 100 之间")
	public String getConstructionOrg() {
		return constructionOrg;
	}

	public void setConstructionOrg(String constructionOrg) {
		this.constructionOrg = constructionOrg;
	}
	
	@Length(min=1, max=32, message="施工单位联系人长度必须介于 1 和 32 之间")
	public String getConstructionContact() {
		return constructionContact;
	}

	public void setConstructionContact(String constructionContact) {
		this.constructionContact = constructionContact;
	}
	
	@Length(min=1, max=32, message="施工单位联系人电话长度必须介于 1 和 32 之间")
	public String getConstructionPhone() {
		return constructionPhone;
	}

	public void setConstructionPhone(String constructionPhone) {
		this.constructionPhone = constructionPhone;
	}
	
	@Length(min=1, max=100, message="工程名称长度必须介于 1 和 100 之间")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Length(min=1, max=100, message="工程地址长度必须介于 1 和 100 之间")
	public String getProjectAddress() {
		return projectAddress;
	}

	public void setProjectAddress(String projectAddress) {
		this.projectAddress = projectAddress;
	}
	
	@Length(min=1, max=500, message="现场检查工程情况长度必须介于 1 和 500 之间")
	public String getSiteSituation() {
		return siteSituation;
	}

	public void setSiteSituation(String siteSituation) {
		this.siteSituation = siteSituation;
	}
	
	@Length(min=1, max=500, message="现场踏勘示意图长度必须介于 1 和 500 之间")
	public String getSitePicture() {
		return sitePicture;
	}

	public void setSitePicture(String sitePicture) {
		this.sitePicture = sitePicture;
	}
	
	@Length(min=1, max=500, message="现场踏勘情况长度必须介于 1 和 500 之间")
	public String getSiteCheckResult() {
		return siteCheckResult;
	}

	public void setSiteCheckResult(String siteCheckResult) {
		this.siteCheckResult = siteCheckResult;
	}
	
	@Length(min=1, max=32, message="勘查人长度必须介于 1 和 32 之间")
	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public Signature getCheckerSig() {
		return checkerSig;
	}

	public void setCheckerSig(Signature checkerSig) {
		this.checkerSig = checkerSig;
	}

	public Signature getPartySig() {
		return partySig;
	}

	public void setPartySig(Signature partySig) {
		this.partySig = partySig;
	}
	
	
}