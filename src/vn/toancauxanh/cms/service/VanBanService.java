package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.springframework.util.SystemPropertyUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Default;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.CoQuanBanHanh;
import vn.toancauxanh.gg.model.LinhVucVanBan;
import vn.toancauxanh.gg.model.QCoQuanBanHanh;
import vn.toancauxanh.gg.model.QLinhVucVanBan;
import vn.toancauxanh.gg.model.QVanBan;
import vn.toancauxanh.gg.model.VanBan;
import vn.toancauxanh.service.BasicService;

public class VanBanService extends BasicService<VanBan>{
	public JPAQuery<VanBan> getTargetQuery(){
		String tukhoa = MapUtils.getString(argDeco(),Labels.getLabel("param.tukhoa"),"").trim();
		Long loaivanban = MapUtils.getLongValue(argDeco(),Labels.getLabel("param.loaivanban"),0);
		Long linhvuc = MapUtils.getLongValue(argDeco(),Labels.getLabel("param.linhvuc"),0);
		Long coquanbanhanh = MapUtils.getLongValue(argDeco(),Labels.getLabel("param.coquanbanhanh"),0);
		Long capbanhanh = MapUtils.getLongValue(argDeco(),Labels.getLabel("param.capbanhanh"),0);
		String trangthaixuatban = MapUtils.getString(argDeco(),Labels.getLabel("param.trangthai"),"").trim();
		Date from = (Date) argDeco().get(Labels.getLabel("param.tungay"));
		Date to = (Date) argDeco().get(Labels.getLabel("param.denngay"));
		JPAQuery<VanBan> q = find(VanBan.class)
				.where(QVanBan.vanBan.trangThai.ne(core().TT_DA_XOA));
		if(tukhoa!=null && !tukhoa.isEmpty()) {
			q.where(QVanBan.vanBan.soKyHieu.like("%"+tukhoa+"%")
				.or(QVanBan.vanBan.trichYeu.like("%"+tukhoa+"%")));
		}
		if(loaivanban!=0) {
			q.where(QVanBan.vanBan.loaiVanBan.id.eq(loaivanban));
		}
		if(linhvuc!=0) {
			q.where(QVanBan.vanBan.linhVucVanBan.id.eq(linhvuc));
		}
		if(coquanbanhanh!=0) {
			q.where(QVanBan.vanBan.coQuanBanHanh.id.eq(coquanbanhanh));
		}
		if(capbanhanh!=0) {
			q.where(QVanBan.vanBan.capBanHanh.id.eq(capbanhanh));
		}
		if(!"".equals(trangthaixuatban)){
			if("true".equals(trangthaixuatban)) {
				q.where(QVanBan.vanBan.xuatBan.eq(true));
			}else {
				q.where(QVanBan.vanBan.xuatBan.eq(false));
			}
		}
		if(from!=null) {
			q.where(QVanBan.vanBan.ngayBanHanh.goe(from));
		}
		if(to!=null) {
			q.where(QVanBan.vanBan.ngayBanHanh.loe(to));
		}
		q.orderBy(QVanBan.vanBan.ngaySua.desc());
		return q;
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
		int page = MapUtils.getIntValue(argDeco(), Labels.getLabel("param.activepage"), 1);
		return page;
	}
	public long getSizeTotal() {
		return getTargetQueryAll().fetchCount();
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
	public JPAQuery<VanBan> getTargetQueryAll(){
		String sokyhieu = MapUtils.getString(argDeco(), Labels.getLabel("param.sokyhieu"));
		String trichyeu = MapUtils.getString(argDeco(), Labels.getLabel("param.trichyeu"));
		Long coquanbanhanh = MapUtils.getLongValue(argDeco(), Labels.getLabel("param.coquanbanhanh"),0);
		Long linhvucvanban = MapUtils.getLongValue(argDeco(), Labels.getLabel("param.linhvuc"),0);
		JPAQuery<VanBan> q = find(VanBan.class)
				.where(QVanBan.vanBan.trangThai.ne(core().TT_DA_XOA))
				.where(QVanBan.vanBan.trangThaiSoan.eq(core().TTS_DA_DUYET))
				.orderBy(QVanBan.vanBan.ngaySua.desc())
				.limit(getPageSize())
				.offset((getPage()-1)*getPageSize());
		if(sokyhieu!=null && !sokyhieu.isEmpty()) {
			q.where(QVanBan.vanBan.soKyHieu.like("%"+sokyhieu+"%"));
		}
		if(trichyeu!=null && !trichyeu.isEmpty()) {
			q.where(QVanBan.vanBan.trichYeu.like("%"+trichyeu+"%"));
		}
		if(coquanbanhanh!=0) {
			q.where(QVanBan.vanBan.coQuanBanHanh.id.eq(coquanbanhanh));
		}
		if(linhvucvanban!=0) {
			q.where(QVanBan.vanBan.linhVucVanBan.id.eq(linhvucvanban));
		}
		return q;
	}
	public JPAQuery<VanBan> getTargetQueryById(){
		Long id = MapUtils.getLongValue(argDeco(), Labels.getLabel("param.id"),0);
		JPAQuery<VanBan> q = find(VanBan.class)
				.where(QVanBan.vanBan.trangThai.ne(core().TT_DA_XOA))
				.where(QVanBan.vanBan.trangThaiSoan.eq(core().TTS_DA_DUYET));
		if(id !=0) {
			q.where(QVanBan.vanBan.id.eq(id));
		}
		return q;
	}
	public JPAQuery<VanBan> getTargetQueryRelated(@Default("0") Long category){
		Long id = MapUtils.getLongValue(argDeco(), Labels.getLabel("param.id"),0);
		JPAQuery<VanBan> q = find(VanBan.class)
				.where(QVanBan.vanBan.trangThai.ne(core().TT_DA_XOA))
				.where(QVanBan.vanBan.trangThaiSoan.eq(core().TTS_DA_DUYET))
				.limit(getPageSize());
		if(id!=0) {
			q.where(QVanBan.vanBan.id.ne(id));
		}
		if(category!=null) {
			q.where(QVanBan.vanBan.loaiVanBan.id.eq(category));
		}
		return q;
	}
	public long getCount(Long idc) {
		JPAQuery<VanBan> q = find(VanBan.class)
				.where(QVanBan.vanBan.linhVucVanBan.trangThai.ne(core().TT_DA_XOA))
				.where(QVanBan.vanBan.linhVucVanBan.trangThai.ne(core().TT_KHONG_AP_DUNG))
				.where(QVanBan.vanBan.linhVucVanBan.id.eq(idc));
		return q.fetchCount();
			
	}
	@Command
	public void searchVanBan(@BindingParam("skh")String skh,
			@BindingParam("ty")String ty,
			@BindingParam("cqbh")String cqbh,
			@BindingParam("lvvb")String lvvb,
			@BindingParam("page") @Default("1")int page){
		String param = "";
		param += ("".equals(param)?"":"&") + (cqbh!=null&&!cqbh.isEmpty()?"coquanbanhanh="+cqbh.trim():"");
		param += ("".equals(param)?"":"&") + (lvvb!=null&&!lvvb.isEmpty()?"linhvuc="+lvvb.trim():"");
		param += ("".equals(param)?"":"&") + (skh!=null&&!skh.isEmpty()?"sokyhieu="+skh.trim():"");
		param += ("".equals(param)?"":"&") + (ty!=null&&!ty.isEmpty()?"trichyeu="+ty.trim():"");
		Executions.getCurrent().sendRedirect("/vanban/vanbanmoi/"+page+"?"+param);
	}
	public LinhVucVanBan getSelectLinhVuc() {
		Long linhvucvanban = MapUtils.getLongValue(argDeco(), Labels.getLabel("param.linhvuc"),0);
		return find(LinhVucVanBan.class).where(QLinhVucVanBan.linhVucVanBan.id.eq(linhvucvanban)).fetchFirst();
	}
	public CoQuanBanHanh getSelectCoQuan() {
		Long coquanbanhanh = MapUtils.getLongValue(argDeco(), Labels.getLabel("param.coquanbanhanh"),0);
		return find(CoQuanBanHanh.class).where(QCoQuanBanHanh.coQuanBanHanh.id.eq(coquanbanhanh)).fetchFirst();
	}
	
}
