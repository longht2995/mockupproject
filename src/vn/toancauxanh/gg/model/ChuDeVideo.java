package vn.toancauxanh.gg.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Window;

import com.google.common.base.Strings;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "chudevideo")
public class ChuDeVideo extends Model<ChuDeVideo> {
	private String tenChuDe = "";
	private String mota = "";

	public String getTenChuDe() {
		return tenChuDe;
	}

	public void setTenChuDe(String tenChuDe) {
		this.tenChuDe = Strings.nullToEmpty(tenChuDe);
	}

	public String getMota() {
		return mota;
	}

	public void setMota(String mota) {
		this.mota = Strings.nullToEmpty(mota);
	}

	private boolean checkApDung;

	@Transient
	public boolean isCheckApDung() {
		checkApDung = false;
		if (core().TT_AP_DUNG.equals(getTrangThai())) {
			checkApDung = true;
		}
		return checkApDung;
	}

	public void setCheckApDung(boolean _isCheckApDung) {
		if (_isCheckApDung) {
			setTrangThai(core().TT_AP_DUNG);
		} else {
			setTrangThai(core().TT_KHONG_AP_DUNG);
		}
		this.checkApDung = _isCheckApDung;
	}

	@Command
	public void saveChuDe(@BindingParam("list") final Object listObject,
			@BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) {
		save();
		wdn.detach();
		System.out.println("Reset"+attr);
		BindUtils.postNotifyChange(null, null, listObject, attr);
	}
	
}
