package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MapUtils;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.LinhVucHoiDap;
import vn.toancauxanh.gg.model.QLinhVucHoiDap;
import vn.toancauxanh.service.BasicService;

public class LinhVucHoiDapService extends BasicService<LinhVucHoiDap> {

	public JPAQuery<LinhVucHoiDap> getTargetQuery() {
		String paramTuKhoa = MapUtils.getString(argDeco(), "tukhoa", "").trim();
		String paramTrangThai = MapUtils.getString(argDeco(), "trangthai", "");

		JPAQuery<LinhVucHoiDap> q = find(LinhVucHoiDap.class);
		// List<LinhVucHoiDap> list = new ArrayList<LinhVucHoiDap>();
		q.where(QLinhVucHoiDap.linhVucHoiDap.trangThai.eq(core().TT_AP_DUNG));

		if (paramTuKhoa != null && !paramTuKhoa.isEmpty()) {
			String key = "%" + paramTuKhoa + "%";
			q.where(QLinhVucHoiDap.linhVucHoiDap.tenLinhVuc.like(key).or(QLinhVucHoiDap.linhVucHoiDap.moTa.like(key)));
		}

		if (!paramTrangThai.isEmpty()) {
			q.where(QLinhVucHoiDap.linhVucHoiDap.trangThai.eq(paramTrangThai));
		}
		return q;
	}

	public List<LinhVucHoiDap> getListLinhVucAnhNull() {
		JPAQuery<LinhVucHoiDap> q = find(LinhVucHoiDap.class);
		List<LinhVucHoiDap> list = new ArrayList<LinhVucHoiDap>();
		list.add(null);
		for (LinhVucHoiDap linhVuc : q.where(QLinhVucHoiDap.linhVucHoiDap.trangThai.ne(core().TT_DA_XOA)).fetch()) {
			list.add(linhVuc);
		}
		return list;
	}
}
