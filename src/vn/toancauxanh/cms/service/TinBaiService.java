package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.springframework.util.SystemPropertyUtils;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.DanhMuc;
import vn.toancauxanh.gg.model.QDanhMuc;
import vn.toancauxanh.gg.model.QThamSo;
import vn.toancauxanh.gg.model.QTinBai;
import vn.toancauxanh.gg.model.ThamSo;
import vn.toancauxanh.gg.model.TinBai;
import vn.toancauxanh.gg.model.enums.ThamSoEnum;
import vn.toancauxanh.service.BasicService;

public class TinBaiService extends BasicService<TinBai> {

	public JPAQuery<TinBai> getTargetQuery() {
		String paramTuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"), "").trim();
		long tacgia_id = MapUtils.getLongValue(argDeco(), Labels.getLabel("param.tacgia"));
		String trangThaiSoan = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthaisoan"), "").trim();
		long chuDe = MapUtils.getLongValue(argDeco(), Labels.getLabel("param.category"));
		String paramTrangThaiNoiBat = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthainoibat"), "");
		String madinhdanh = MapUtils.getString(argDeco(), Labels.getLabel("param.madinhdanh"), "").trim();

		JPAQuery<TinBai> q = find(TinBai.class).where(QTinBai.tinBai.trangThai.ne(core().TT_DA_XOA));

		if (paramTuKhoa != null && !paramTuKhoa.isEmpty()) {
			String tukhoa = "%" + paramTuKhoa + "%";
			q.where(QTinBai.tinBai.title.like(tukhoa).or(QTinBai.tinBai.description.like(tukhoa)));
		}

		if (tacgia_id > 0) {
			q.where(QTinBai.tinBai.author.id.eq(tacgia_id));
		}
		if (trangThaiSoan != null && !trangThaiSoan.isEmpty()) {
			q.where(QTinBai.tinBai.trangThaiSoan.eq(trangThaiSoan));
		}
		if (chuDe > 0) {
			DanhMuc category = em().find(DanhMuc.class, chuDe);
			List<DanhMuc> children = find(DanhMuc.class).where(QDanhMuc.danhMuc.parent.eq(category))
					.where(QDanhMuc.danhMuc.trangThai.eq(core().TT_AP_DUNG)).fetch();
			q.where(QTinBai.tinBai.categories.any().in(children).or(QTinBai.tinBai.categories.contains(category)));
		}

		if ("true".equals(paramTrangThaiNoiBat)) {
			q.where(QTinBai.tinBai.noiBat.eq(true));
		} else if ("false".equals(paramTrangThaiNoiBat)) {
			q.where(QTinBai.tinBai.noiBat.eq(false));
		}

		if (getFixTuNgay() != null && getFixDenNgay() == null) {
			q.where(QTinBai.tinBai.dateBeginTime.after(getFixTuNgay()));
		} else if (getFixTuNgay() == null && getFixDenNgay() != null) {
			q.where(QTinBai.tinBai.dateBeginTime.before(getFixDenNgay()));
		} else if (getFixTuNgay() != null && getFixDenNgay() != null) {
			q.where(QTinBai.tinBai.dateBeginTime.between(getFixTuNgay(), getFixDenNgay()));
		}

		if (madinhdanh != null && !madinhdanh.isEmpty()) {
			String ma = "%" + madinhdanh + "%";
			q.where(QTinBai.tinBai.maDinhDanh.like(ma));
		}
		return q.orderBy(QTinBai.tinBai.ngayTao.desc()).limit(getPageSize())
				.offset((getPage()-1)*getPageSize());
	}

	public List<TinBai> getListTinBaiAndNull() {
		JPAQuery<TinBai> q = find(TinBai.class);
		List<TinBai> list = new ArrayList<TinBai>();
		list.add(null);
		for (TinBai tin : q.where(QTinBai.tinBai.trangThai.eq(core().TT_AP_DUNG)).fetch()) {
			list.add(tin);
		}
		return list;
	}

	// =========================================================================

	public List<TinBai> getTop5ByThamSo(ThamSoEnum ts) {
		List<TinBai> list = new ArrayList<TinBai>();
		if (ts != null) {
			ThamSo objTS = find(ThamSo.class).where(QThamSo.thamSo.trangThai.ne(core().TT_DA_XOA))
					.where(QThamSo.thamSo.ma.eq(ts)).fetchFirst();
			if (objTS.getValue() > 0) {
				DanhMuc objDanhMuc = find(DanhMuc.class).where(QDanhMuc.danhMuc.trangThai.ne(core().TT_DA_XOA))
						.where(QDanhMuc.danhMuc.id.eq(objTS.getValue())).fetchFirst();
				if (objDanhMuc != null) {
					list = find(TinBai.class).where(QTinBai.tinBai.trangThai.ne(core().TT_DA_XOA))
							.where(QTinBai.tinBai.categories.contains(objDanhMuc)).limit(5)
							.orderBy(QTinBai.tinBai.ngaySua.desc()).fetch();
				}
			}
		}
		return list;
	}

	// public TinBai getListTinBaiById(final long id) {
	// return
	// find(TinBai.class).where(QTinBai.tinBai.trangThai.ne(core().TT_DA_XOA)).where(QTinBai.tinBai.id.eq(id))
	// .fetchOne();
	// }

	// ==============================================================================

	public List<TinBai> getTop5TinBaiLienQuan(DanhMuc objDanhMuc) {
		List<TinBai> list = new ArrayList<TinBai>();
		JPAQuery<TinBai> q = find(TinBai.class).where(
				QTinBai.tinBai.trangThai.ne(core().TT_DA_XOA).and(QTinBai.tinBai.trangThai.eq(core().TT_AP_DUNG)));
		if (objDanhMuc != null) {
			q.where(QTinBai.tinBai.categories.contains(objDanhMuc));
			list = q.fetch();
		}
		return list;
	}

	public List<TinBai> getListTinBaiByDanhMuc(long id) {
		List<TinBai> list = new ArrayList<TinBai>();
		JPAQuery<TinBai> q = find(TinBai.class).where(
				QTinBai.tinBai.trangThai.ne(core().TT_DA_XOA).and(QTinBai.tinBai.trangThai.eq(core().TT_AP_DUNG)));
		try {
			DanhMuc obj = find(DanhMuc.class).where(QDanhMuc.danhMuc.trangThai.ne(core().TT_DA_XOA))
					.where(QDanhMuc.danhMuc.id.eq(id)).fetchFirst();
			if (obj != null) {
				list = q.where(QTinBai.tinBai.categories.contains(obj)).fetch();
			}
		} catch (NumberFormatException e) {
		}
		return list;
	}

	public JPAQuery<TinBai> getListTinBaiSlide() {
		JPAQuery<TinBai> q = find(TinBai.class).where(QTinBai.tinBai.trangThai.ne(core().TT_DA_XOA))
				.where(QTinBai.tinBai.noiBat.eq(true)).orderBy(QTinBai.tinBai.ngaySua.desc());
		return q;
	}
	
	//==========================================================
	
	public int getPageSize() {
		int pagesize = MapUtils.getIntValue(getArg(), 5,
				5);
		if (pagesize == 0) {
			pagesize = 5;
		}
		return pagesize;
	}
	
	public int getPage() {
		int page = MapUtils.getIntValue(argDeco(), Labels.getLabel("param.activepage"), 1);
		return page;
	}
	
	public long getSizeTotal() {
		return getTargetQuery().fetchCount();
	}
	
	public List<Integer> getRecord() {
		List<Integer> list = new ArrayList<Integer>();
		int currentpage = getPage();
		int totalpage;
		totalpage = 1;
		float total = getSizeTotal()/getPageSize();
		if(getSizeTotal()%getPageSize()>0) {
			totalpage = (int) total +1;
		}else {
			totalpage= (int)total;
		}
		if(totalpage==1) return list;
		int range =5;
		int min =0;
		int max = 0;
		if(totalpage<= range) {
			min = 1;
			max = totalpage;
		}else {
			min = currentpage-((range/2)+1);
			max = currentpage+((range/2)-1);
			if(min < 1) {
				min = 1;
				max = range;
			}
			else if(max > totalpage) {
				max = totalpage;
				min = (totalpage-range)+1;
			}
		}
		for(int i = min;i<=max;i++) {
			list.add(i);
		}
		return list;
	}
	public TinBai getTinbai(){
		long tinbai_id = MapUtils.getLongValue(argDeco(),"id");
		JPAQuery<TinBai> q = find(TinBai.class).where(QTinBai.tinBai.trangThai.ne(core().TT_DA_XOA)).where(QTinBai.tinBai.trangThaiSoan.eq(core().TTS_DA_DUYET));
		if (tinbai_id > 0) {
			q.where(QTinBai.tinBai.id.eq(tinbai_id));
		}
		return q.fetchCount() > 0 ? q.fetchFirst() : null;
	}

	// ==========================================================

}
