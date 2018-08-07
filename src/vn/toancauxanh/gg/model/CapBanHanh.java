package vn.toancauxanh.gg.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Window;

import vn.toancauxanh.model.Model;

@Entity
@Table(name="capbanhanh")
public class CapBanHanh extends Model<CapBanHanh>{
	private String tenBanHanh;
	private String moTa;
	public String getTenBanHanh() {
		return tenBanHanh;
	}
	public void setTenBanHanh(String tenBanHanh) {
		this.tenBanHanh = tenBanHanh;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	@Command
	public void saveBanHanh(@BindingParam("list") final Object listObject,
			@BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) {
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);
	}
}
