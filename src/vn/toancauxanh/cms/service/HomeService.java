package vn.toancauxanh.cms.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.collections.MapUtils;
import org.springframework.util.SystemPropertyUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Sessions;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.Banner;
import vn.toancauxanh.gg.model.QBanner;
import vn.toancauxanh.gg.model.QVanBan;
import vn.toancauxanh.gg.model.QVideo;
import vn.toancauxanh.gg.model.VanBan;
import vn.toancauxanh.gg.model.Video;
import vn.toancauxanh.service.BasicService;

public class HomeService extends BasicService<Object> {

	public HomeService() {
		super();
		// init();
		// login();
		
		String key = getClass() + "." + "accessed";
		if (!Sessions.getCurrent().hasAttribute(key)) {
			Sessions.getCurrent().setAttribute(key, "1");
		}
	}
	
	public String formatDate(Date d) {
		return new SimpleDateFormat("dd/MM/yyyy").format(d);
	}

	public String formatDayOfCalendar(Date d) {
		return new SimpleDateFormat("dd").format(d);
	}

	public String formatMonthYearOfCalendar(Date d) {
		return new SimpleDateFormat("MM/yyyy").format(d);
	}

	@Override
	public Date getTuNgay() {
		return date(Labels.getLabel("param.tungay"));
	}

	@Override
	public Date getDenNgay() {
		return date(Labels.getLabel("param.denngay"));
	}

	public boolean isTimChinhXac() {
		boolean exactly = MapUtils.getBooleanValue(getArg(), "chinhxac");
		return exactly;
	}

	public long getCid() {
		return MapUtils.getLongValue(argDeco(), "id");
	}

	public int getPageSize() {
		int pagesize = MapUtils.getIntValue(getArg(), SystemPropertyUtils.resolvePlaceholders(PH_KEYPAGESIZE),
				defaultPageSize());
		if (pagesize == 0) {
			pagesize = 10;
		}
		return pagesize;
	}

	public int getPage() {
		int trang = MapUtils.getIntValue(getArg(), "page");
		return trang;
	}
	public JPAQuery<Video> getTargetQueryVideo(){
		JPAQuery<Video> q = find(Video.class)
				.where(QVideo.video.trangThai.ne(core().TT_KHONG_AP_DUNG))
				.where(QVideo.video.ngayXuatBan.before(new Date()).or(QVideo.video.ngayXuatBan.isNull()))
				.where(QVideo.video.ngayHetHan.isNull().or(QVideo.video.ngayHetHan.after(new Date())))
				.limit(3).orderBy(QVideo.video.ngaySua.desc());
		return q;
	}
	public JPAQuery<VanBan> getTargetQueryVanBan(){
		JPAQuery<VanBan> q = find(VanBan.class)
				.where(QVanBan.vanBan.trangThai.ne(core().TT_DA_XOA))
				.where(QVanBan.vanBan.trangThaiSoan.eq(core().TTS_DA_DUYET))
				.limit(getPageSize()).orderBy(QVanBan.vanBan.ngaySua.desc());
		return q;
	}
	public Banner getTargetQueryBannerLeftTop(){
		JPAQuery<Banner> q = find(Banner.class)
				.where(QBanner.banner.locationBanner.eq("baner-body-1"))
				.orderBy(QBanner.banner.soThuTu.desc());
		return q.fetchFirst();
	}
	public Banner getTargetQueryBannerLeftMidle(){
		JPAQuery<Banner> q = find(Banner.class)
				.where(QBanner.banner.locationBanner.eq("baner-body-2"))
				.orderBy(QBanner.banner.soThuTu.desc());
		return q.fetchFirst();
	}
	public Banner getTargetQueryBannerLeftBottom(){
		JPAQuery<Banner> q = find(Banner.class)
				.where(QBanner.banner.locationBanner.eq("baner-body-3"))
				.orderBy(QBanner.banner.soThuTu.desc());
		return q.fetchFirst();
	}
	public Banner getTargetQueryBannerRightTop(){
		JPAQuery<Banner> q = find(Banner.class)
				.where(QBanner.banner.locationBanner.eq("baner-right-1"))
				.orderBy(QBanner.banner.soThuTu.desc());
		return q.fetchFirst();
	}
	public Banner getTargetQueryBannerRightMidle(){
		JPAQuery<Banner> q = find(Banner.class)
				.where(QBanner.banner.locationBanner.eq("baner-right-2"))
				.orderBy(QBanner.banner.soThuTu.desc());
		return q.fetchFirst();
	}
	public Banner getTargetQueryBannerRightBottom(){
		JPAQuery<Banner> q = find(Banner.class)
				.where(QBanner.banner.locationBanner.eq("baner-right-3"))
				.orderBy(QBanner.banner.soThuTu.desc());
		return q.fetchFirst();
	}
}
