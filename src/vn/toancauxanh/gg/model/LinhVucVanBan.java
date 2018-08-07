package vn.toancauxanh.gg.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Window;

import vn.toancauxanh.model.Model;

@Entity
@Table(name="linhvucvanban")
public class LinhVucVanBan extends Model<LinhVucVanBan>{
	private String tenLinhVuc = "";
	private String moTa = "";
	public String getTenLinhVuc() {
		return tenLinhVuc;
	}
	public void setTenLinhVuc(String tenLinhVuc) {
		this.tenLinhVuc = tenLinhVuc;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	@Command
	public void saveLinhVuc(@BindingParam("list") final Object listObject,
			@BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) {
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);
	}
}
