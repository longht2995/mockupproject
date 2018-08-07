package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.LoaiVanBan;
import vn.toancauxanh.gg.model.QLoaiVanBan;
import vn.toancauxanh.service.BasicService;

public class LoaiVanBanService extends BasicService<LoaiVanBan>{
	public JPAQuery<LoaiVanBan> getTargetQuery() {
		String tukhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"));
		String trangthai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"),"");
		JPAQuery<LoaiVanBan> q = find(LoaiVanBan.class)
				.where(QLoaiVanBan.loaiVanBan.trangThai.ne(core().TT_DA_XOA));
		if(tukhoa!=null) {
			q.where(QLoaiVanBan.loaiVanBan.tenLoai.like("%"+tukhoa+"%"));
		}
		if(trangthai.equals("ap_dung")) {
			q.where(QLoaiVanBan.loaiVanBan.trangThai.eq(core().TT_AP_DUNG));
		}else if(trangthai.equals("khong_ap_dung")) {
			q.where(QLoaiVanBan.loaiVanBan.trangThai.eq(core().TT_KHONG_AP_DUNG));
		}
		q.orderBy(QLoaiVanBan.loaiVanBan.ngaySua.desc());
		return q;
	}
	public List<LoaiVanBan> getListLoaiVanBan(){
		List<LoaiVanBan> list = new ArrayList<LoaiVanBan>();
		list = find(LoaiVanBan.class)
				.where(QLoaiVanBan.loaiVanBan.trangThai.ne(core().TT_KHONG_AP_DUNG))
				.orderBy(QLoaiVanBan.loaiVanBan.ngaySua.desc())
				.fetch();
		return list;
	}
	public List<LoaiVanBan> getListLoaiVanBanAndNull(){
		List<LoaiVanBan> list = new ArrayList<LoaiVanBan>();
		list.add(null);
		list.addAll(getListLoaiVanBan());
		return list;
	}
}
