package vn.toancauxanh.cms.service;

import java.util.ArrayList;

import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.ListModelList;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.LinhVucVanBan;
import vn.toancauxanh.gg.model.QLinhVucVanBan;
import vn.toancauxanh.gg.model.QLoaiVanBan;
import vn.toancauxanh.service.BasicService;

public class LinhVucVanBanService extends BasicService<LinhVucVanBan>{
	public JPAQuery<LinhVucVanBan> getTargetQuery(){
		String tukhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"));
		String trangthai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"),"");
		JPAQuery<LinhVucVanBan> q = find(LinhVucVanBan.class)
				.where(QLinhVucVanBan.linhVucVanBan.trangThai.ne(core().TT_DA_XOA));
		q.orderBy(QLinhVucVanBan.linhVucVanBan.ngaySua.desc());
		if(tukhoa!=null && !tukhoa.isEmpty()) {
			q.where(QLinhVucVanBan.linhVucVanBan.tenLinhVuc.like("%"+tukhoa+"%"));
		}
		if(trangthai.equals("ap_dung")) {
			q.where(QLinhVucVanBan.linhVucVanBan.trangThai.eq(core().TT_AP_DUNG));
		}else if(trangthai.equals("khong_ap_dung")) {
			q.where(QLinhVucVanBan.linhVucVanBan.trangThai.eq(core().TT_KHONG_AP_DUNG));
		}
		return q;
	}
	public List<LinhVucVanBan> getListLinhVucVanBan(){
		List<LinhVucVanBan> list = new ArrayList<LinhVucVanBan>();
		list = find(LinhVucVanBan.class)
				.where(QLinhVucVanBan.linhVucVanBan.trangThai.ne(core().TT_KHONG_AP_DUNG))
				.orderBy(QLinhVucVanBan.linhVucVanBan.ngaySua.desc()).fetch();
		return list;
	}
	public JPAQuery<LinhVucVanBan> getTargetQueryLinhVuc(){
		JPAQuery<LinhVucVanBan> q = find(LinhVucVanBan.class)
				.where(QLinhVucVanBan.linhVucVanBan.trangThai.ne(core().TT_DA_XOA))
				.where(QLinhVucVanBan.linhVucVanBan.trangThai.ne(core().TT_KHONG_AP_DUNG));
		return q;
	}
	public List<LinhVucVanBan> getListLinhVucVanBanAndNull(){
		List<LinhVucVanBan> list = new ArrayList<LinhVucVanBan>();
		list.add(null);
		list.addAll(getListLinhVucVanBan());
		return list;
	}
}
