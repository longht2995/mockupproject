package vn.toancauxanh.cms.service;

import java.util.Date;

import org.apache.commons.collections.MapUtils;
import org.zkoss.bind.annotation.Default;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.QVideo;
import vn.toancauxanh.gg.model.Video;
import vn.toancauxanh.service.BasicService;

public class VideoService extends BasicService<Video>{
	
	public JPAQuery<Video> getTargetQuery(){
		String tukhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"));
		String trangthaisoan = MapUtils.getString(argDeco(),  Labels.getLabel("param.trangthaisoan"),"");
		JPAQuery<Video> q = find(Video.class);
		if(tukhoa!=null) {
			q.where(QVideo.video.tieuDe.like("%"+tukhoa+"%")
					.or(QVideo.video.moTa.like("%"+tukhoa+"%")));
		}
		if(trangthaisoan.equals("true")) {
			q.where(QVideo.video.xuatBan.eq(true));
		}else if(trangthaisoan.equals("false")) {
			q.where(QVideo.video.xuatBan.eq(false));
		}
		q.orderBy(QVideo.video.ngaySua.desc());
		return q;
	}
	public JPAQuery<Video> getTargetQueryVideo(@Default("0")Long id){
		String chudevideo = MapUtils.getString(argDeco(), Labels.getLabel("param.chudevideo"));
		JPAQuery<Video> q = find(Video.class)
				.where(QVideo.video.trangThai.ne(core().TT_KHONG_AP_DUNG))
				.where(QVideo.video.ngayXuatBan.before(new Date()).or(QVideo.video.ngayXuatBan.isNull()))
				.where(QVideo.video.ngayHetHan.isNull().or(QVideo.video.ngayHetHan.after(new Date())));
		if(id!=null) {
			q.where(QVideo.video.id.ne(id));
		}
		if(chudevideo!=null) {
			q.where(QVideo.video.chuDeVideo.id.eq(Long.parseLong(chudevideo)));
		}
		return q;
	}
	public JPAQuery<Video> getTargetQueryNoiBatOrById(){
		String chudevideo = MapUtils.getString(argDeco(), Labels.getLabel("param.chudevideo"));
		String id = MapUtils.getString(argDeco(), Labels.getLabel("param.idvideo"));
		JPAQuery<Video> q = find(Video.class)
				.where(QVideo.video.ngayXuatBan.before(new Date()).or(QVideo.video.ngayXuatBan.isNull()))
				.where(QVideo.video.ngayHetHan.isNull().or(QVideo.video.ngayHetHan.after(new Date())));
		if(chudevideo!=null) {
			q.where(QVideo.video.chuDeVideo.id.eq(Long.parseLong(chudevideo)));
		}
		if(id!=null) {
			q.where(QVideo.video.id.eq(Long.parseLong(id)));
		}
		q.orderBy(QVideo.video.ngaySua.desc());
		return q;
	}
	
}
