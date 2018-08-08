package vn.toancauxanh.cms.service;

import org.apache.commons.collections.MapUtils;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.QThongBao;
import vn.toancauxanh.gg.model.ThongBao;
import vn.toancauxanh.service.BasicService;

public class ThongBaoService extends BasicService<ThongBaoService>{
	public JPAQuery<ThongBao> getTargetQuery() {
		String param = MapUtils.getString(argDeco(),Labels.getLabel("param.tukhoa"),"").trim();
		String trangThai = MapUtils.getString(argDeco(),Labels.getLabel("param.trangthai"),"");
		JPAQuery<ThongBao> q = find(ThongBao.class)
				.where(QThongBao.thongBao.trangThai.ne(core().TT_DA_XOA));
		if (param != null && !param.isEmpty()) {
			String tukhoa = "%" + param + "%";
			q.where(QThongBao.thongBao.tieuDe.like(tukhoa));
		}
		if (!trangThai.isEmpty()) {
			q.where(QThongBao.thongBao.trangThai.eq(trangThai));
		}
		q.orderBy(QThongBao.thongBao.ngaySua.desc());
		return q;
	}
}
