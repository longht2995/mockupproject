package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.CapBanHanh;
import vn.toancauxanh.gg.model.QCapBanHanh;
import vn.toancauxanh.gg.model.QCoQuanBanHanh;
import vn.toancauxanh.service.BasicService;

public class CapBanHanhService extends BasicService<CapBanHanh>{
	public JPAQuery<CapBanHanh> getTargetQuery(){
		String tukhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"));
		String trangthai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"),"");
		JPAQuery<CapBanHanh> q = find(CapBanHanh.class)
				.where(QCapBanHanh.capBanHanh.trangThai.ne(core().TT_DA_XOA));
		if(tukhoa!=null) {
			q.where(QCapBanHanh.capBanHanh.tenBanHanh.like("%"+tukhoa+"%"));
		}
		if(trangthai.equals("ap_dung")) {
			q.where(QCapBanHanh.capBanHanh.trangThai.eq(core().TT_AP_DUNG));
		}else if(trangthai.equals("khong_ap_dung")) {
			q.where(QCapBanHanh.capBanHanh.trangThai.eq(core().TT_KHONG_AP_DUNG));
		}
		q.orderBy(QCapBanHanh.capBanHanh.ngaySua.desc());
		return q;
	}
	public List<CapBanHanh> getListCapBanHanh(){
		List<CapBanHanh> list = new ArrayList<CapBanHanh>();
		list = find(CapBanHanh.class)
				.where(QCapBanHanh.capBanHanh.trangThai.ne(core().TT_KHONG_AP_DUNG))
				.orderBy(QCapBanHanh.capBanHanh.ngaySua.desc())
				.fetch();
		return list;
	}
	public List<CapBanHanh> getListCapBanHanhAndNull(){
		List<CapBanHanh> list = new ArrayList<CapBanHanh>();
		list.add(null);
		list.addAll(getListCapBanHanh());
		return list;
	}
}
