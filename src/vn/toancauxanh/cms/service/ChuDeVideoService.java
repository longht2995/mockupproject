package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.ChuDeVideo;
import vn.toancauxanh.gg.model.QChuDeVideo;
import vn.toancauxanh.service.BasicService;

public class ChuDeVideoService extends BasicService<ChuDeVideo>{
	public JPAQuery<ChuDeVideo> getTargetQuery(){
		String tuKhoa = MapUtils.getString(argDeco(),Labels.getLabel("param.tukhoa"),"");
		String trangThai = MapUtils.getString(argDeco(),Labels.getLabel("param.trangthai"),"");
		JPAQuery<ChuDeVideo> q = find(ChuDeVideo.class)
				.where(QChuDeVideo.chuDeVideo.trangThai.ne(core().TT_DA_XOA));
		if(tuKhoa!=null && !tuKhoa.isEmpty()) {
			q.where(QChuDeVideo.chuDeVideo.tenChuDe.like('%'+tuKhoa+'%'));
		}
		if(!trangThai.isEmpty()) {
			q.where(QChuDeVideo.chuDeVideo.trangThai.eq(trangThai));
		}
		q.orderBy(QChuDeVideo.chuDeVideo.ngaySua.desc());
		return q;
	}
	public List<ChuDeVideo> getListChuDeVideo(){
		List<ChuDeVideo> list = new ArrayList<ChuDeVideo>();
		list = find(ChuDeVideo.class)
				.where(QChuDeVideo.chuDeVideo.trangThai.ne(core().TT_KHONG_AP_DUNG))
				.orderBy(QChuDeVideo.chuDeVideo.ngaySua.desc())
				.fetch();
		return list;
	}
	public List<ChuDeVideo> getListChuDeVideoAndNull(){
		List<ChuDeVideo> list = new ArrayList<ChuDeVideo>();
		list.add(null);
		list.addAll(getListChuDeVideo());
		return list;
	}
}
