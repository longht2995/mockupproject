package vn.toancauxanh.gg.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Window;

import com.google.common.base.Strings;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "linhvuchoidap")
public class LinhVucHoiDap extends Model<LinhVucHoiDap> {

	private String tenLinhVuc = "";
	private String moTa = "";

	public LinhVucHoiDap() {
	}

	public String getTenLinhVuc() {
		return tenLinhVuc;
	}

	public void setTenLinhVuc(String tenLinhVuc) {
		this.tenLinhVuc = Strings.nullToEmpty(tenLinhVuc);
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = Strings.nullToEmpty(moTa);
	}

	// ============================================================

	// @command('saveLinhVuc',list=vmArgs, wdn=wdn)
	@Command
	public void saveLinhVuc(@BindingParam("list") Object list, @BindingParam("wdn") Window wdn) {
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, list, "*");
	}

}
