/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.material.entity;

import java.util.Calendar;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import org.wxjs.les.common.persistence.DataEntity;
import org.wxjs.les.modules.base.entity.Signature;

/**
 * 视听资料证据Entity
 * @author 千里目
 * @version 2018-07-23
 */
public class Material extends DataEntity<Material> {
	
	private static final long serialVersionUID = 1L;
	private Date getDate;		// 收集时间
	private String materialType;		// 收集方式
	private String getLocation;		// 收集地点
	
	private String materialPath;		// 资料路径
	private String materialComment;		// 资料说明
	
	private Signature getterSig = new Signature(); 		// 收集人签名
	private Signature partySig = new Signature();		// 当事人签名
	
	private Date beginDate;
	private Date endDate;
	
	private String content; //拍摄内容
	
	public Material() {
		super();
	}

	public Material(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getGetDate() {
		return getDate;
	}

	public void setGetDate(Date getDate) {
		this.getDate = getDate;
	}
	
	@Length(min=1, max=32, message="收集方式长度必须介于 1 和 32 之间")
	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	
	@Length(min=1, max=100, message="收集地点长度必须介于 1 和 100 之间")
	public String getGetLocation() {
		return getLocation;
	}

	public void setGetLocation(String getLocation) {
		this.getLocation = getLocation;
	}
	
	
	
	public Signature getGetterSig() {
		return getterSig;
	}

	public void setGetterSig(Signature getterSig) {
		this.getterSig = getterSig;
	}

	public Signature getPartySig() {
		return partySig;
	}

	public void setPartySig(Signature partySig) {
		this.partySig = partySig;
	}

	@Length(min=0, max=300, message="资料路径长度必须介于 0 和 300 之间")
	public String getMaterialPath() {
		return materialPath;
	}

	public void setMaterialPath(String materialPath) {
		this.materialPath = materialPath;
	}
	
	@Length(min=0, max=500, message="资料说明长度必须介于 0 和 500 之间")
	public String getMaterialComment() {
		return materialComment;
	}

	public void setMaterialComment(String materialComment) {
		this.materialComment = materialComment;
	}

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	
	
	
}