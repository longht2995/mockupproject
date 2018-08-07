package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.CoQuanBanHanh;
import vn.toancauxanh.gg.model.QCoQuanBanHanh;
import vn.toancauxanh.gg.model.QLoaiVanBan;
import vn.toancauxanh.service.BasicService;

public class CoQuanBanHanhService extends BasicService<CoQuanBanHanh>{
	public JPAQuery<CoQuanBanHanh> getTargetQuery(){
		String tukhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"));
		String trangthai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"),"");
		JPAQuery<CoQuanBanHanh> q = find(CoQuanBanHanh.class)
				.where(QCoQuanBanHanh.coQuanBanHanh.trangThai.ne(core().TT_DA_XOA));
		if(tukhoa!=null) {
			q.where(QCoQuanBanHanh.coQuanBanHanh.tenCoQuan.like("%"+tukhoa+"%"));
		}
		if(trangthai.equals("ap_dung")) {
			q.where(QCoQuanBanHanh.coQuanBanHanh.trangThai.eq(core().TT_AP_DUNG));
		}else if(trangthai.equals("khong_ap_dung")) {
			q.where(QCoQuanBanHanh.coQuanBanHanh.trangThai.eq(core().TT_KHONG_AP_DUNG));
		}
		q.orderBy(QCoQuanBanHanh.coQuanBanHanh.ngaySua.desc());
		return q;
	}
	public List<CoQuanBanHanh> getListCoQuanBanHanh(){
		List<CoQuanBanHanh> list = new ArrayList<CoQuanBanHanh>();
		list = find(CoQuanBanHanh.class)
				.where(QCoQuanBanHanh.coQuanBanHanh.trangThai.ne(core().TT_KHONG_AP_DUNG))
				.orderBy(QCoQuanBanHanh.coQuanBanHanh.ngaySua.desc())
				.fetch();
		return list;
	}
	public List<CoQuanBanHanh> getListCoQuanBanHanhAndNull(){
		List<CoQuanBanHanh> list = new ArrayList<CoQuanBanHanh>();
		list.add(null);
		list.addAll(getListCoQuanBanHanh());
		return list;
	}
}

