package vn.toancauxanh.cms.service;

import org.apache.commons.collections.MapUtils;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.HoiDap;
import vn.toancauxanh.gg.model.QHoiDap;
import vn.toancauxanh.service.BasicService;

public class HoiDapService extends BasicService<HoiDap> {

	public JPAQuery<HoiDap> getTargetQuery() {
		String paramtukhoa = MapUtils.getString(argDeco(), "tukhoa", "").trim();
		String paramTrangThaiTraLoi = MapUtils.getString(argDeco(), "trangthaitraloi", "");
		long paramlinhvuc = MapUtils.getLongValue(argDeco(), "linhvuchoidap");
		JPAQuery<HoiDap> q = find(HoiDap.class);
		q.where(QHoiDap.hoiDap.trangThai.ne(core().TT_DA_XOA));
		if (paramtukhoa != null && !paramtukhoa.isEmpty()) {
			String key = "%" + paramtukhoa + "%";
			q.where(QHoiDap.hoiDap.tieuDe.like(key).or(QHoiDap.hoiDap.noiDung.like(key))
					.or(QHoiDap.hoiDap.nguoiHoi.like(key)).or(QHoiDap.hoiDap.email.like(key)));
		}
		if (paramTrangThaiTraLoi != null && !paramTrangThaiTraLoi.isEmpty()) {
			q.where(QHoiDap.hoiDap.trangThaiTraLoi.eq(paramTrangThaiTraLoi));
		}

		if (paramlinhvuc > 0) {
			q.where(QHoiDap.hoiDap.linhVuc.id.eq(paramlinhvuc));
		}

		return q;
	}
}
