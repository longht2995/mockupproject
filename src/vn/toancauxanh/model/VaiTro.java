package vn.toancauxanh.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Window;

import com.google.common.base.Strings;
import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.service.Quyen;

@Entity
@Table(name = "vaitro", uniqueConstraints = @UniqueConstraint(columnNames = { "tenVaiTro" }), indexes = {
		@Index(columnList = "alias"), @Index(columnList = "tenVaiTro") })
public class VaiTro extends Model<VaiTro> {
	public static transient final Logger LOG = LogManager.getLogger(VaiTro.class.getName());

	public static final String QUANTRIHETHONG = "quantrihethong";
	public static final String BIENTAPTINBAI = "bientaptinbai";
	
	public static final String[] VAITRO_DEFAULTS = {QUANTRIHETHONG, BIENTAPTINBAI};

	private Set<String> quyens = new HashSet<>();
	private Set<String> quyenEdits = quyens;
	private String tenVaiTro = "";
	private String alias = "";
	private int soThuTu;

	public VaiTro() {
		super();
	}

	public VaiTro(String ten, String quyen) {
		super();
		tenVaiTro = ten;
		setAlias(quyen.trim());
	}

	public int getSoThuTu() {
		return soThuTu;
	}

	public void setSoThuTu(int soThuTu) {
		this.soThuTu = soThuTu;
	}

	@Transient
	public List<NhanVien> getListNhanVien() {
		JPAQuery<NhanVien> q = find(NhanVien.class)
				.where(QNhanVien.nhanVien.trangThai.ne(core().TT_DA_XOA))
				.where(QNhanVien.nhanVien.vaiTros.contains(this));
		return q.fetch();
	}

	Set<TreeNode<String[]>> selectedItems = new HashSet<>();

	@Transient
	@NotifyChange({ "selectedItems", "model", "*" })
	public DefaultTreeModel<String[]> getModel() {
		getQuyens();
		selectedItems.clear();
		
		final HashSet<TreeNode<String[]>> openItems_ = new HashSet<>();
		
		final TreeNode<String[]> rootNode = new DefaultTreeNode<>(new String[] {}, new ArrayList<DefaultTreeNode<String[]>>());
		
		final DefaultTreeModel<String[]> model = new DefaultTreeModel<>(rootNode, true);
		
		model.setMultiple(true);
		final Set<String> allQuyens = new HashSet<>();
		
		final long q = find(VaiTro.class).fetchCount();
		
		if(q==0){
			for (String vaiTro : VAITRO_DEFAULTS) {
				allQuyens.addAll(getQuyenMacDinhs(vaiTro));
			}
		} else {
			allQuyens.addAll(getQuyenAllMacDinhs());
		}
		
		for (String resource : core().getRESOURCES()) {
			DefaultTreeNode<String[]> parentNode = new DefaultTreeNode<>(
					new String[] { Labels.getLabel("url." + resource + ".mota"), resource },
					new ArrayList<DefaultTreeNode<String[]>>());
			if (quyens.contains(resource)) {
				selectedItems.add(parentNode);
				openItems_.add(parentNode);
				model.setOpenObjects(openItems_);
			}
			for (String action : core().getACTIONS()) {
				String quyen = resource + Quyen.CACH + action;
				if (allQuyens.contains(quyen)) {
					DefaultTreeNode<String[]> childNode = new DefaultTreeNode<>(
							new String[] { Labels.getLabel("action." + action + ".mota"), quyen },
							new ArrayList<DefaultTreeNode<String[]>>());
					if (quyens.contains(quyen)) {
						selectedItems.add(childNode);
						openItems_.add(childNode);
						openItems_.add(parentNode);
					}
					parentNode.add(childNode);
				}
			}
			rootNode.add(parentNode);
		}
		quyenEdits = new HashSet<>(quyens);
		model.setOpenObjects(openItems_);
		return model;
	}

	public String getAlias() {
		return alias;
	}


	//Chạy lên 
	@Transient
	public Set<String> getQuyenAllMacDinhs() {
		Set<String> quyens1 = new HashSet<>();
		
		quyens1.add(core().VAITROTHEM);
		quyens1.add(core().VAITROLIST);
		quyens1.add(core().VAITROSUA);
		quyens1.add(core().VAITROXOA);
		quyens1.add(core().VAITROXEM);
		
		quyens1.add(core().NGUOIDUNGTHEM);
		quyens1.add(core().NGUOIDUNGSUA);
		quyens1.add(core().NGUOIDUNGXEM);
		quyens1.add(core().NGUOIDUNGLIST);
		quyens1.add(core().NGUOIDUNGXOA);
		
		quyens1.add(core().HOIDAPTHEM);
		quyens1.add(core().HOIDAPSUA);
		quyens1.add(core().HOIDAPXEM);
		quyens1.add(core().HOIDAPLIST);
		quyens1.add(core().HOIDAPXOA);		
		
		quyens1.add(core().CHUDEVIDEOTHEM);
		quyens1.add(core().CHUDEVIDEOSUA);
		quyens1.add(core().CHUDEVIDEOXEM);
		quyens1.add(core().CHUDEVIDEOLIST);
		quyens1.add(core().CHUDEVIDEOXOA);
		
		quyens1.add(core().VIDEOTHEM);
		quyens1.add(core().VIDEOSUA);
		quyens1.add(core().VIDEOXEM);
		quyens1.add(core().VIDEOLIST);
		quyens1.add(core().VIDEOXOA);
		
		quyens1.add(core().THONGBAOTHEM);
		quyens1.add(core().THONGBAOSUA);
		quyens1.add(core().THONGBAOXEM);
		quyens1.add(core().THONGBAOLIST);
		quyens1.add(core().THONGBAOXOA);
		
		quyens1.add(core().BANNERTHEM);
		quyens1.add(core().BANNERSUA);
		quyens1.add(core().BANNERXEM);
		quyens1.add(core().BANNERLIST);
		quyens1.add(core().BANNERXOA);
		
		quyens1.add(core().VANBANTHEM);
		quyens1.add(core().VANBANSUA);
		quyens1.add(core().VANBANXEM);
		quyens1.add(core().VANBANLIST);
		quyens1.add(core().VANBANXOA);
		
		quyens1.add(core().QUANTRIHETHONGLIST);
		
		quyens1.add(core().CATEGORYTHEM);
		quyens1.add(core().CATEGORYSUA);
		quyens1.add(core().CATEGORYXEM);
		quyens1.add(core().CATEGORYLIST);
		quyens1.add(core().CATEGORYXOA);
		
		quyens1.add(core().BAIVIETTHEM);
		quyens1.add(core().BAIVIETSUA);
		quyens1.add(core().BAIVIETXEM);
		quyens1.add(core().BAIVIETLIST);
		quyens1.add(core().BAIVIETXOA);
		quyens1.add(core().BAIVIETXUATBAN);
		
		quyens1.add(core().THONGKELIST);
		
		return quyens1;
	}
	//Add các quyền mặc định cho Vai trò tương ứng
	@Transient
	public Set<String> getQuyenMacDinhs(String alias1) {
		Set<String> quyens1 = new HashSet<>();
		if (!alias1.isEmpty()) {
			if (QUANTRIHETHONG.equals(alias1)) {
				
				quyens1.add(core().VAITROTHEM);
				quyens1.add(core().VAITROLIST);
				quyens1.add(core().VAITROSUA);
				quyens1.add(core().VAITROXOA);
				quyens1.add(core().VAITROXEM);
				
				quyens1.add(core().NGUOIDUNGTHEM);
				quyens1.add(core().NGUOIDUNGSUA);
				quyens1.add(core().NGUOIDUNGXEM);
				quyens1.add(core().NGUOIDUNGLIST);
				quyens1.add(core().NGUOIDUNGXOA);
				
				quyens1.add(core().HOIDAPTHEM);
				quyens1.add(core().HOIDAPSUA);
				quyens1.add(core().HOIDAPXEM);
				quyens1.add(core().HOIDAPLIST);
				quyens1.add(core().HOIDAPXOA);		
				
				quyens1.add(core().CHUDEVIDEOTHEM);
				quyens1.add(core().CHUDEVIDEOSUA);
				quyens1.add(core().CHUDEVIDEOXEM);
				quyens1.add(core().CHUDEVIDEOLIST);
				quyens1.add(core().CHUDEVIDEOXOA);
				
				quyens1.add(core().VIDEOTHEM);
				quyens1.add(core().VIDEOSUA);
				quyens1.add(core().VIDEOXEM);
				quyens1.add(core().VIDEOLIST);
				quyens1.add(core().VIDEOXOA);
				
				quyens1.add(core().THONGBAOTHEM);
				quyens1.add(core().THONGBAOSUA);
				quyens1.add(core().THONGBAOXEM);
				quyens1.add(core().THONGBAOLIST);
				quyens1.add(core().THONGBAOXOA);
				
				quyens1.add(core().BANNERTHEM);
				quyens1.add(core().BANNERSUA);
				quyens1.add(core().BANNERXEM);
				quyens1.add(core().BANNERLIST);
				quyens1.add(core().BANNERXOA);
				
				quyens1.add(core().VANBANTHEM);
				quyens1.add(core().VANBANSUA);
				quyens1.add(core().VANBANXEM);
				quyens1.add(core().VANBANLIST);
				quyens1.add(core().VANBANXOA);
				
				quyens1.add(core().QUANTRIHETHONGLIST);
				
				quyens1.add(core().CATEGORYTHEM);
				quyens1.add(core().CATEGORYSUA);
				quyens1.add(core().CATEGORYXEM);
				quyens1.add(core().CATEGORYLIST);
				quyens1.add(core().CATEGORYXOA);
				
				quyens1.add(core().BAIVIETTHEM);
				quyens1.add(core().BAIVIETSUA);
				quyens1.add(core().BAIVIETXEM);
				quyens1.add(core().BAIVIETLIST);
				quyens1.add(core().BAIVIETXOA);
				quyens1.add(core().BAIVIETXUATBAN);
				
				quyens1.add(core().THONGKELIST);
			} else if (BIENTAPTINBAI.equals(alias1)) {
				quyens1.add(core().BAIVIETTHEM);
				quyens1.add(core().BAIVIETSUA);
				quyens1.add(core().BAIVIETXEM);
				quyens1.add(core().BAIVIETLIST);
				quyens1.add(core().BAIVIETXOA);
				quyens1.add(core().BAIVIETXUATBAN);
			}
			
		}
		return quyens1;
	}

	@Transient
	public Set<String> getQuyenMacDinhs() {
		return getQuyenMacDinhs(getAlias());
	}
	
	@ElementCollection(fetch = FetchType.EAGER)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	@CollectionTable(name = "vaitro_quyens", joinColumns = {@JoinColumn(name = "vaitro_id")})
	public Set<String> getQuyens() {
		if (quyens.isEmpty()) {
			quyens.addAll(getQuyenMacDinhs());
		}
		return quyens;
	}

	public String getTenVaiTro() {
		return tenVaiTro;
	}

	public void setAlias(String alias1) {
		this.alias = Strings.nullToEmpty(alias1);
	}

	public void setQuyens(final Set<String> dsChoPhep) {
		quyens = dsChoPhep;
	}

	public void setTenVaiTro(final String _tenVaiTro) {
		tenVaiTro = Strings.nullToEmpty(_tenVaiTro);
	}

	@Override
	public String toString() {
		return super.toString() + " " + tenVaiTro;
	}

	@Transient
	public Set<TreeNode<String[]>> getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(Set<TreeNode<String[]>> selectedItems_) {
		Set<TreeNode<String[]>> selectedItemsTmp = new HashSet<>();
		selectedItemsTmp.addAll(selectedItems);
		for (final TreeNode<String[]> node : selectedItems) {
			if (!selectedItems_.contains(node)) {
				quyenEdits.remove(node.getData()[1]);
				selectedItemsTmp.remove(node);

				// remove parent
				TreeNode<String[]> pNode = node.getParent();
				if (pNode != null && selectedItems_.contains(pNode)) {
					quyenEdits.remove(pNode.getData()[1]);
					selectedItemsTmp.remove(pNode);
				}
				// remove all child
				if (node.getChildCount() > 0) {
					for (TreeNode<String[]> n : node.getChildren()) {
						quyenEdits.remove(n.getData()[1]);
						selectedItemsTmp.remove(n);
					}
				}
			}
		}
		for (final TreeNode<String[]> node : selectedItems_) {
			if (!selectedItems.contains(node)) {
				quyenEdits.add(node.getData()[1]);
				selectedItemsTmp.add(node);
				if (node.getChildCount() > 0) {
					for (TreeNode<String[]> n : node.getChildren()) {
						quyenEdits.add(n.getData()[1]);
						selectedItemsTmp.add(n);
					}
				}
			}
		}
		selectedItems = selectedItemsTmp;
		BindUtils.postNotifyChange(null, null, this, "quyenEdits");
		BindUtils.postNotifyChange(null, null, this, "selectedItems");
	}

	private boolean checkApDung;

	@Transient
	public boolean isCheckApDung() {
		checkApDung = false;
		if (core().TT_AP_DUNG.equals(getTrangThai())) {
			checkApDung = true;
		}
		return checkApDung;
	}

	public void setCheckApDung(boolean _isCheckApDung) {
		if (_isCheckApDung) {
			setTrangThai(core().TT_AP_DUNG);
		} else {
			setTrangThai(core().TT_KHONG_AP_DUNG);
		}
		this.checkApDung = _isCheckApDung;
	}

	private boolean checkKichHoat;

	public boolean isCheckKichHoat() {
		return checkKichHoat;
	}

	public void setCheckKichHoat(boolean checkKichHoat) {
		this.checkKichHoat = checkKichHoat;
	}
	@Command
	public void saveVaiTro(@BindingParam("list") final Object listObject,
			@BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) {
		setTenVaiTro(getTenVaiTro().trim().replaceAll("\\s+", " "));
		setQuyens(quyenEdits);
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, "vaiTroQuery");
	}
	@Override
	public void save() {
		setTenVaiTro(getTenVaiTro().trim().replaceAll("\\s+", " "));
		setQuyens(quyenEdits);
		if (noId()) {
			showNotification("Đã lưu thành công!", "", "success");
		} else {
			showNotification("Đã cập nhật thành công!", "", "success");
		}
		doSave();
	}
	@Transient
	public boolean isMacDinh() {
		return Arrays.asList(VAITRO_DEFAULTS).contains(this.getAlias());
	}
}
