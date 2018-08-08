package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.DanhMuc;
import vn.toancauxanh.gg.model.QDanhMuc;
import vn.toancauxanh.gg.model.QThamSo;
//import vn.toancauxanh.gg.model.QThamSo;
import vn.toancauxanh.gg.model.ThamSo;
import vn.toancauxanh.gg.model.enums.ThamSoEnum;
import vn.toancauxanh.service.BasicService;

public class ThamSoService extends BasicService<ThamSo> {

	public ThamSo getTieuDeHoiDap() {
		JPAQuery<ThamSo> q = find(ThamSo.class).where(
				QThamSo.thamSo.trangThai.ne(core().TT_DA_XOA).and(QThamSo.thamSo.ma.eq(ThamSoEnum.TIEU_DE_HOI_DAP)));
		ThamSo thamSo = q.fetchFirst();
		if (thamSo == null) {
			thamSo = new ThamSo();
			thamSo.setMa(ThamSoEnum.TIEU_DE_HOI_DAP);
			thamSo.setName(ThamSoEnum.TIEU_DE_HOI_DAP.getText());
			thamSo.saveNotShowNotification();
		}
		return thamSo;
	}

	// ======================================================================

	private ThamSo thamSoSelect;
	private DanhMuc danhMucSelect;

	public DanhMuc getDanhMucSelect() {
		return danhMucSelect;
	}

	public void setDanhMucSelect(DanhMuc danhMucSelect) {
		this.danhMucSelect = danhMucSelect;
	}

	public ThamSo getThamSoSelect() {
		return thamSoSelect;
	}

	public void setThamSoSelect(ThamSo thamSoSelect) {
		this.thamSoSelect = thamSoSelect;
	}

	public void isFirst() {
		ThamSo tstt = new ThamSo();
		tstt.setMa(ThamSoEnum.CAT_ID_TINTUC);
		tstt.setValue(0l);
		tstt.setName("Cập nhật ".concat(ThamSoEnum.CAT_ID_TINTUC.getText()).toUpperCase());
		tstt.saveNotShowNotification();

		ThamSo tsgt = new ThamSo();
		tsgt.setMa(ThamSoEnum.CAT_ID_GIOITHIEU);
		tsgt.setValue(0l);
		tsgt.setName("Cập nhật ".concat(ThamSoEnum.CAT_ID_GIOITHIEU.getText()).toUpperCase());
		tsgt.saveNotShowNotification();

		ThamSo tslh = new ThamSo();
		tslh.setMa(ThamSoEnum.CAT_ID_LIENHE);
		tslh.setValue(0l);
		tslh.setName("Cập nhật ".concat(ThamSoEnum.CAT_ID_LIENHE.getText()).toUpperCase());
		tslh.saveNotShowNotification();

		ThamSo tstthdndh = new ThamSo();
		tstthdndh.setMa(ThamSoEnum.CAT_ID_THUONG_TRUC_HDND);
		tstthdndh.setValue(0l);
		tstthdndh.setName(ThamSoEnum.CAT_ID_THUONG_TRUC_HDND.getText().toUpperCase());
		tstthdndh.saveNotShowNotification();

		ThamSo tscbhdndh = new ThamSo();
		tscbhdndh.setMa(ThamSoEnum.CAT_ID_CAC_BAN_HDND_HUYEN);
		tscbhdndh.setValue(0l);
		tscbhdndh.setName(ThamSoEnum.CAT_ID_CAC_BAN_HDND_HUYEN.getText().toUpperCase());
		tscbhdndh.saveNotShowNotification();

		ThamSo tshdndx = new ThamSo();
		tshdndx.setMa(ThamSoEnum.CAT_ID_HDND_XA);
		tshdndx.setValue(0l);
		tshdndx.setName(ThamSoEnum.CAT_ID_HDND_XA.getText().toUpperCase());
		tshdndx.saveNotShowNotification();

		ThamSo tsgschdnd = new ThamSo();
		tsgschdnd.setMa(ThamSoEnum.CAT_ID_GIAM_SAT_CUA_HDND);
		tsgschdnd.setValue(0l);
		tsgschdnd.setName(ThamSoEnum.CAT_ID_GIAM_SAT_CUA_HDND.getText().toUpperCase());
		tsgschdnd.saveNotShowNotification();
	}

	@Init
	public void init() {
		List<ThamSo> list = find(ThamSo.class).where(QThamSo.thamSo.trangThai.ne(core().TT_DA_XOA)).fetch();
		if (list == null || list.isEmpty()) {
			isFirst();
		}
	}

	public List<ThamSo> getList() {
		List<ThamSo> list = new ArrayList<ThamSo>();
		JPAQuery<ThamSo> q = find(ThamSo.class).where(QThamSo.thamSo.trangThai.ne(core().TT_DA_XOA));
		list.addAll(q.fetch());
		if (list.isEmpty() || list == null) {
			isFirst();
		}
		return list;
	}

	public DanhMuc getDanhMucByThamSo(ThamSo objThamSo) {
		DanhMuc objDanhMuc = new DanhMuc();
		JPAQuery<DanhMuc> q = find(DanhMuc.class).where(QDanhMuc.danhMuc.trangThai.ne(core().TT_DA_XOA));
		if (objThamSo.getValue() != null) {
			q.where(QDanhMuc.danhMuc.id.eq(objThamSo.getValue()));
			objDanhMuc = q.fetchFirst();
		}
		return objDanhMuc;
	}

	@Command
	public void onSelectThamSo() {
		if (thamSoSelect != null) {
			danhMucSelect = getDanhMucByThamSo(thamSoSelect);
		}
		BindUtils.postNotifyChange(null, null, this, "danhMucSelect");
	}

	// ============================================================================

	public ThamSo getChuDeHDNDHuyen() {
		JPAQuery<ThamSo> q = find(ThamSo.class).where(QThamSo.thamSo.trangThai.ne(core().TT_DA_XOA));
		q.where(QThamSo.thamSo.ma.eq(ThamSoEnum.CAT_ID_THUONG_TRUC_HDND));
		return q.fetchCount() > 0 ? q.fetchFirst() : null;
	}
	
	public ThamSo getCacBanHDNDHuyen() {
		JPAQuery<ThamSo> q = find(ThamSo.class).where(QThamSo.thamSo.trangThai.ne(core().TT_DA_XOA));
		q.where(QThamSo.thamSo.ma.eq(ThamSoEnum.CAT_ID_CAC_BAN_HDND_HUYEN));
		return q.fetchCount() > 0 ? q.fetchFirst() : null;
	}
	
	public ThamSo getHDNDXa() {
		JPAQuery<ThamSo> q = find(ThamSo.class).where(QThamSo.thamSo.trangThai.ne(core().TT_DA_XOA));
		q.where(QThamSo.thamSo.ma.eq(ThamSoEnum.CAT_ID_HDND_XA));
		return q.fetchCount() > 0 ? q.fetchFirst() : null;
	}
	
	public ThamSo getGiamSatCuaHDND() {
		JPAQuery<ThamSo> q = find(ThamSo.class).where(QThamSo.thamSo.trangThai.ne(core().TT_DA_XOA));
		q.where(QThamSo.thamSo.ma.eq(ThamSoEnum.CAT_ID_GIAM_SAT_CUA_HDND));
		return q.fetchCount() > 0 ? q.fetchFirst() : null;
	}
	

}
