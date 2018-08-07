package vn.greenglobal.front.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;

import vn.toancauxanh.gg.model.DanhMuc;
import vn.toancauxanh.model.NhanVien;

public class HeaderService extends FrontService {

	private DanhMuc gioiThieu;
	private DanhMuc tinTuc;
	private NhanVien user;

	public NhanVien getUser() {
		if (user == null) {
			user = getNhanVien();
		}
		return user;
	}

	@Command
	public void search() {
		String tuKhoa = MapUtils.getString(argDeco(), "tukhoa");
		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			String param = "";
			param = ("".equals(param) ? "" : param + "&") + (tuKhoa != null ? "tukhoa=" + tuKhoa.trim() : "");
			Executions.sendRedirect("/timkiem?" + param);
		}
	}

	public boolean isOpen(String resource, String cat) {
		if (cat.equals("tintuc")) {
			for (DanhMuc each : tinTuc.getChild()) {
				if (each.getAlias().equals(resource)) {
					return true;
				}
			}
		}

		if (cat.equals("gioithieu")) {
			for (DanhMuc each : gioiThieu.getChild()) {
				if (each.getAlias().equals(resource)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void redirectLogin(HttpServletRequest req, HttpServletResponse res) {
		// K redirect
	}
}
