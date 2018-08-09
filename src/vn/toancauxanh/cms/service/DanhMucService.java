package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.TreeNode;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.DanhMuc;
import vn.toancauxanh.gg.model.QDanhMuc;
import vn.toancauxanh.gg.model.QThamSo;
import vn.toancauxanh.gg.model.ThamSo;
import vn.toancauxanh.gg.model.enums.ThamSoEnum;
import vn.toancauxanh.service.BasicService;

public class DanhMucService extends BasicService<DanhMuc> {

	public JPAQuery<DanhMuc> getTargetQuery() {
		String paramTuKhoa = MapUtils.getString(argDeco(), "tukhoa", "").trim();
		String trangThai = MapUtils.getString(argDeco(), "trangthai", "");
		Long maDanhMuc = MapUtils.getLongValue(argDeco(), "cat");
		JPAQuery<DanhMuc> q = find(DanhMuc.class).where(QDanhMuc.danhMuc.trangThai.ne(core().TT_DA_XOA));

		if (paramTuKhoa != null && !paramTuKhoa.isEmpty()) {
			String tukhoa = "%" + paramTuKhoa + "%";
			q.where(QDanhMuc.danhMuc.description.like(tukhoa));
		}
		if (!trangThai.isEmpty()) {
			q.where(QDanhMuc.danhMuc.trangThai.eq(trangThai));
		}
		if (maDanhMuc > 0 || maDanhMuc != null) {
			q = getChild(maDanhMuc);
		}
		return q.orderBy(QDanhMuc.danhMuc.ngaySua.desc());
	}

	private String img = "/backend/assets/img/edit.png";
	private String hoverImg = "/backend/assets/img/edit_hover.png";
	private String strUpdate = "Thứ tự";
	private boolean update = true;
	private boolean updateThanhCong = true;

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getHoverImg() {
		return hoverImg;
	}

	public void setHoverImg(String hoverImg) {
		this.hoverImg = hoverImg;
	}

	public String getStrUpdate() {
		return strUpdate;
	}

	public void setStrUpdate(String strUpdate) {
		this.strUpdate = strUpdate;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public boolean isUpdateThanhCong() {
		return updateThanhCong;
	}

	public void setUpdateThanhCong(boolean updateThanhCong) {
		this.updateThanhCong = updateThanhCong;
	}

	public void openObject(DefaultTreeModel<DanhMuc> model, TreeNode<DanhMuc> node) {
		if (node.isLeaf()) {
			model.addOpenObject(node);
		} else {
			for (TreeNode<DanhMuc> child : node.getChildren()) {
				model.addOpenObject(child);
				openObject(node.getModel(), child);
			}
		}
	}

	public DefaultTreeModel<DanhMuc> getModel() {

		String param = MapUtils.getString(argDeco(), "tukhoa", "").trim();
		String trangThai = MapUtils.getString(argDeco(), "trangthai", "");

		DanhMuc danhMucGoc = new DanhMuc();
		DefaultTreeModel<DanhMuc> model = new DefaultTreeModel<DanhMuc>(danhMucGoc.getNode(), true);
		for (DanhMuc danhMuc : getList()) {

			if ((danhMuc.getName().toLowerCase().contains(param.toLowerCase())
					&& danhMuc.getTrangThai().contains(trangThai)) || danhMuc.loadSizeChild() > 0) {
				danhMucGoc.getNode().add(danhMuc.getNode());
			}
		}

		if (!param.isEmpty() || !"".equals(param) || !trangThai.isEmpty() || !"".equals(trangThai)) {
			danhMucGoc.getNode().getModel().setOpenObjects(danhMucGoc.getNode().getChildren());
		}

		openObject(model, danhMucGoc.getNode());
		BindUtils.postNotifyChange(null, null, this, "sizeOfCategories");
		return model;
	}

	public List<DanhMuc> getList() {
		JPAQuery<DanhMuc> q = find(DanhMuc.class);
		q.where(QDanhMuc.danhMuc.trangThai.ne(core().TT_DA_XOA)).where(QDanhMuc.danhMuc.parent.isNull());
		q.orderBy(QDanhMuc.danhMuc.soThuTu.asc());
		List<DanhMuc> list = new ArrayList<DanhMuc>();
		if (q.fetchCount() > 0) {
			list = q.fetch();
			for (DanhMuc danhMuc : list) {
				danhMuc.loadChildren();
			}
		}
		return list;
	}

	public List<DanhMuc> getListParent() {
		JPAQuery<DanhMuc> q = find(DanhMuc.class);
		q.where(QDanhMuc.danhMuc.trangThai.ne(core().TT_DA_XOA)).where(QDanhMuc.danhMuc.parent.isNull());
		q.orderBy(QDanhMuc.danhMuc.soThuTu.asc());
		List<DanhMuc> list = new ArrayList<DanhMuc>();
		list.add(null);
		if (q.fetchCount() > 0) {
			list = q.fetch();
		}
		return list;
	}

	public List<DanhMuc> getDanhMucCon(DanhMuc danhMuc) {
		List<DanhMuc> list = new ArrayList<>();
		if (!danhMuc.getTrangThai().equalsIgnoreCase(core().TT_DA_XOA)) {
			for (TreeNode<DanhMuc> el : danhMuc.getNode().getChildren()) {
				list.add(el.getData());
				list.addAll(getDanhMucCon(el.getData()));
			}
		}
		return list;
	}

	// dùng để kiểm tra danh sách chủ đề
	// có rỗng không
	public long getSizeOfCategories() {
		String param = MapUtils.getString(argDeco(), "tukhoa", "").trim();
		String trangThai = MapUtils.getString(argDeco(), "trangthai", "");
		JPAQuery<DanhMuc> q = find(DanhMuc.class).where(QDanhMuc.danhMuc.trangThai.ne(core().TT_DA_XOA));

		if (!trangThai.isEmpty()) {
			q.where(QDanhMuc.danhMuc.trangThai.eq(trangThai));
		}
		if (!param.isEmpty()) {
			q.where(QDanhMuc.danhMuc.name.toLowerCase().contains(param.toLowerCase()));
		}
		return q.fetchCount();
	}

	// =========================================

	public List<DanhMuc> getList2() {
		JPAQuery<DanhMuc> q = find(DanhMuc.class);
		q.where(QDanhMuc.danhMuc.trangThai.ne(core().TT_DA_XOA)).where(QDanhMuc.danhMuc.parent.isNull());
		q.orderBy(QDanhMuc.danhMuc.soThuTu.asc());

		List<DanhMuc> list = new ArrayList<DanhMuc>();
		if (q.fetchCount() > 0) {
			list = q.fetch();
			for (DanhMuc category : list) {
				category.loadChildren();
			}
		}
		return list;
	}

	public List<DanhMuc> getListAllCategory() {

		List<DanhMuc> list = new ArrayList<>();
		for (DanhMuc danhMuc : getList2()) {
			list.add(danhMuc);
			list.addAll(getDanhMucCon(danhMuc));
		}
		return list;
	}

	public List<DanhMuc> getListAllCategoryAndNullButThis(DanhMuc self) {

		// nếu là thêm mới thì self bằng null
		List<DanhMuc> list = new ArrayList<>();
		list.add(null);
		for (DanhMuc cat : getListAllButThis(self)) {
			list.add(cat);
			list.addAll(getCategoryChildrenButThis(cat, self));
		}
		return list;
	}

	public List<DanhMuc> getListAllButThis(DanhMuc self) {
		JPAQuery<DanhMuc> q = find(DanhMuc.class);
		q.where(QDanhMuc.danhMuc.trangThai.ne(core().TT_DA_XOA)).where(QDanhMuc.danhMuc.parent.isNull());
		q.orderBy(QDanhMuc.danhMuc.soThuTu.asc());

		// không lấy lại chính nó (dùng khi chỉnh sửa)
		if (self != null && !self.noId()) {
			q.where(QDanhMuc.danhMuc.id.ne(self.getId()));
		}

		List<DanhMuc> list = new ArrayList<DanhMuc>();
		if (q.fetchCount() > 0) {
			list = q.fetch();
			for (DanhMuc category : list) {
				category.loadChildren();
			}
		}

		return list;
	}

	// bỏ qua, không lấy thằng cha của nó
	public List<DanhMuc> getCategoryChildrenButThis(DanhMuc danhMuc, DanhMuc ignore) {
		List<DanhMuc> list = new ArrayList<>();
		if (!danhMuc.getTrangThai().equals(core().TT_DA_XOA)) {
			for (TreeNode<DanhMuc> el : danhMuc.getNode().getChildren()) {
				if (ignore.getId() != el.getData().getId()) {
					list.add(el.getData());
					list.addAll(getCategoryChildrenButThis(el.getData(), ignore));
				}
			}
		}
		return list;
	}

	// =================================================================================================

	@Command
	public void clickButton(@BindingParam("model") final List<DanhMuc> model) {
		if (strUpdate.equals("Thứ tự")) {
			setStrUpdate("Lưu thứ tự");
			setImg("/backend/assets/img/save.png");
			setHoverImg("/backend/assets/img/save_hover.png");
			setUpdate(false);
		} else {
			setUpdateThanhCong(true);

			if (model == null) {
				for (DanhMuc cat : listChuDeThayDoiThuTu) {
					if (check(cat)) {
						setUpdateThanhCong(false);
						break;
					}
					cat.save();
				}
			} else {
				for (DanhMuc cat : model) {
					if (check(cat)) {
						setUpdateThanhCong(false);
						break;
					}
					cat.save();
				}
			}

			if (isUpdateThanhCong()) {
				setStrUpdate("Thứ tự");
				setImg("/backend/assets/img/edit.png");
				setHoverImg("/backend/assets/img/edit_hover.png");
				setUpdate(true);
			} else {
				setUpdateThanhCong(updateThanhCong);
			}
		}
		BindUtils.postNotifyChange(null, null, this, "img");
		BindUtils.postNotifyChange(null, null, this, "hoverImg");
		BindUtils.postNotifyChange(null, null, this, "update");
		BindUtils.postNotifyChange(null, null, this, "strUpdate");
		BindUtils.postNotifyChange(null, null, this, "updateThanhCong");
		BindUtils.postNotifyChange(null, null, this, "list");
		BindUtils.postNotifyChange(null, null, this, "model");
		BindUtils.postNotifyChange(null, null, this, "targetQueryTheLoai");
	}

	private boolean check(DanhMuc cat) {
		if (cat.getSoThuTu() <= 0) {
			return true;
		}
		return false;
	}

	private List<DanhMuc> listChuDeThayDoiThuTu = new ArrayList<>();

	public List<DanhMuc> getListChuDeThayDoiThuTu() {
		return listChuDeThayDoiThuTu;
	}

	public void setListChuDeThayDoiThuTu(List<DanhMuc> listChuDeThayDoiThuTu) {
		this.listChuDeThayDoiThuTu = listChuDeThayDoiThuTu;
	}

	public void addListChuDeThayDoiThuTu(DanhMuc category, int stt) {
		if (listChuDeThayDoiThuTu.contains(category)) {
			listChuDeThayDoiThuTu.remove(category);
			category.setSoThuTu(stt);
			listChuDeThayDoiThuTu.add(category);
		} else {
			category.setSoThuTu(stt);
			listChuDeThayDoiThuTu.add(category);
		}
	}

	// ===========================================================================

	public List<DanhMuc> getListAllCategoryAndNull() {
		List<DanhMuc> list = new ArrayList<>();
		list.add(null);
		for (DanhMuc danhMuc : getList()) {
			list.add(danhMuc);
			list.addAll(getDanhMucCon(danhMuc));
		}
		return list;
	}

	// ================================================================
	public DanhMuc getById() {
		Long maDanhMuc = MapUtils.getLongValue(argDeco(), "cat");
		JPAQuery<DanhMuc> q = find(DanhMuc.class).where(
				QDanhMuc.danhMuc.trangThai.ne(core().TT_DA_XOA).and(QDanhMuc.danhMuc.trangThai.eq(core().TT_AP_DUNG)));

		if (maDanhMuc != null && maDanhMuc > 0) {
			q.where(QDanhMuc.danhMuc.id.eq(maDanhMuc));
		}
		return q.fetchCount() > 0 ? q.fetchFirst() : null;
	}

	public JPAQuery<DanhMuc> getChild(Long parentId) {
		if (parentId != null && parentId > 0) {
			JPAQuery<DanhMuc> q = find(DanhMuc.class)
					.where(QDanhMuc.danhMuc.trangThai.ne(core().TT_DA_XOA).and(QDanhMuc.danhMuc.parent.id.eq(parentId))
							.and(QDanhMuc.danhMuc.trangThai.eq(core().TT_AP_DUNG)));
			return q.orderBy(QDanhMuc.danhMuc.soThuTu.asc());
		}
		return find(DanhMuc.class).where(QDanhMuc.danhMuc.id.eq(0l));
	}

	public JPAQuery<DanhMuc> getChild() {
		long chuDe = MapUtils.getLongValue(argDeco(), "cat");

		if (chuDe > 0) {
			JPAQuery<DanhMuc> q = find(DanhMuc.class).where(QDanhMuc.danhMuc.trangThai.ne(core().TT_DA_XOA)
					.and(QDanhMuc.danhMuc.parent.id.eq(chuDe)).and(QDanhMuc.danhMuc.trangThai.eq(core().TT_AP_DUNG)));
			System.out.println("ffffffff" + q.fetchCount());
			return q.fetchCount() > 0 ? q.orderBy(QDanhMuc.danhMuc.soThuTu.asc()) : q.where(QDanhMuc.danhMuc.id.eq(chuDe));
		}
		return find(DanhMuc.class).where(QDanhMuc.danhMuc.id.eq(0l));
	}

	// ======================================================================

	public DanhMuc getChuDeTinTuc() {
		ThamSo objTS = null;
		objTS = find(ThamSo.class).where(QThamSo.thamSo.trangThai.ne(core().TT_DA_XOA))
				.where(QThamSo.thamSo.ma.eq(ThamSoEnum.CAT_ID_TINTUC)).fetchFirst();
		DanhMuc objDM = new DanhMuc();
		JPAQuery<DanhMuc> q = find(DanhMuc.class).where(QDanhMuc.danhMuc.trangThai.ne(core().TT_DA_XOA));
		if (objTS != null) {
			q.where(QDanhMuc.danhMuc.id.eq(objTS.getValue()));
			objDM = q.fetchFirst();
			return objDM;
		} else {
			return null;
		}
	}

	public DanhMuc getChuDeGioiThieu() {
		ThamSo objTS = null;
		objTS = find(ThamSo.class).where(QThamSo.thamSo.trangThai.ne(core().TT_DA_XOA))
				.where(QThamSo.thamSo.ma.eq(ThamSoEnum.CAT_ID_GIOITHIEU)).fetchFirst();
		DanhMuc objDM = new DanhMuc();
		JPAQuery<DanhMuc> q = find(DanhMuc.class).where(QDanhMuc.danhMuc.trangThai.ne(core().TT_DA_XOA));
		if (objTS != null) {
			q.where(QDanhMuc.danhMuc.id.eq(objTS.getValue()));
			objDM = q.fetchFirst();
			return objDM;
		} else {
			return null;
		}
	}

	public DanhMuc getChuDeLienHe() {
		ThamSo objTS = null;
		objTS = find(ThamSo.class).where(QThamSo.thamSo.trangThai.ne(core().TT_DA_XOA))
				.where(QThamSo.thamSo.ma.eq(ThamSoEnum.CAT_ID_LIENHE)).fetchFirst();
		DanhMuc objDM = new DanhMuc();
		JPAQuery<DanhMuc> q = find(DanhMuc.class).where(QDanhMuc.danhMuc.trangThai.ne(core().TT_DA_XOA));
		if (objTS != null) {
			q.where(QDanhMuc.danhMuc.id.eq(objTS.getValue()));
			objDM = q.fetchFirst();
			return objDM;
		} else {
			return null;
		}
	}

	public List<DanhMuc> getCdtt() {
		DanhMuc dm = getChuDeTinTuc();
		List<DanhMuc> list = null;
		if (dm != null) {
			list = dm.getChild();
			return list.size() > 0 ? list : null;
		}
		return null;
	}

	public List<DanhMuc> getCdgt() {
		DanhMuc dm = getChuDeGioiThieu();
		List<DanhMuc> list = null;
		if (dm != null) {
			list = dm.getChild();
			return list.size() > 0 ? list : null;
		}
		return null;
	}

	public List<DanhMuc> getCdlh() {
		DanhMuc dm = getChuDeLienHe();
		List<DanhMuc> list = null;
		if (dm != null) {
			list = dm.getChild();
			return list.size() > 0 ? list : null;
		}
		return null;
	}
}
