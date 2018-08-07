package vn.toancauxanh.gg.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Window;

import com.google.common.base.Strings;
import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "danhmuc")
public class DanhMuc extends Model<DanhMuc> {

	private String name = "";
	private String description = "";
	private DanhMuc parent;
	private String alias = "";
	private int soThuTu;

	public DanhMuc() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = Strings.nullToEmpty(name);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = Strings.nullToEmpty(description);
	}

	@ManyToOne
	public DanhMuc getParent() {
		return parent;
	}

	public void setParent(DanhMuc parent) {
		this.parent = parent;
	}

	public String getAlias() {
		if (alias.isEmpty() && "".equals(alias)) {
			alias = unAccent(getName());
		}
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = Strings.nullToEmpty(alias);
	}

	public int getSoThuTu() {
		return soThuTu;
	}

	public void setSoThuTu(int soThuTu) {
		this.soThuTu = soThuTu;
	}

	private transient final TreeNode<DanhMuc> node = new DefaultTreeNode<DanhMuc>(this,
			new ArrayList<DefaultTreeNode<DanhMuc>>());

	@Transient
	public TreeNode<DanhMuc> getNode() {
		return node;
	}

	@Transient
	public String getChildName() {
		int count = 0;
		String s = " ";
		for (DanhMuc cha = getParent(); cha != null; cha = cha.getParent())
			count++;
		for (int i = 0; i <= count; i++)
			s += " - ";
		return s + this.name;
	}

	public void loadChildren() {
		for (final DanhMuc con : find(DanhMuc.class).where(QDanhMuc.danhMuc.parent.eq(this))
				.where(QDanhMuc.danhMuc.trangThai.ne(core().TT_DA_XOA)).orderBy(QDanhMuc.danhMuc.soThuTu.asc())
				.fetch()) {
			con.loadChildren();
			node.add(con.getNode()); // danh sách treenode<DanhMuc>
		}
	}

	public int loadSizeChild() {
		int size = core().getDanhMuc().getDanhMucCon(this).size();
		return size;
	}

	// ============================================================================================================================

	@Command
	// @command('saveChuDeMain',list=vmArgs, wdn=wdn)
	public void saveChuDeMain(@BindingParam("list") final Object listObject, @BindingParam("wdn") final Window wdn) {
		setName(getName().trim().replaceAll("\\s+", " "));
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, "*");
	}

	@Command
	// @command('saveChude',node=node, tree=tree,isAdd=isAdd)
	public void saveChude(@BindingParam("node") org.zkoss.zul.DefaultTreeNode<DanhMuc> node1,
			@BindingParam("tree") org.zkoss.zul.DefaultTreeModel<DanhMuc> tree, @BindingParam("isAdd") boolean isAdd) {
		List<DanhMuc> list = find(DanhMuc.class).where(QDanhMuc.danhMuc.parent.eq(getParent()))
				.where(QDanhMuc.danhMuc.trangThai.ne(core().TT_DA_XOA)).fetch();
		if (isAdd) {
			setSoThuTu(list.size() + 1);
			node1.add(getNode());
		}
		setName(getName().trim().replaceAll("\\s+", " "));
		save();
		tree.addOpenObject(node1);
		BindUtils.postNotifyChange(null, null, node1, "*");
	}

	@Command
	// @command('deleteChuDe',node=node,tree=tree.getModel(),catSelected=vmchild)
	public void deleteChuDe(final @BindingParam("node") org.zkoss.zul.DefaultTreeNode<DanhMuc> node1,
			final @BindingParam("tree") org.zkoss.zul.DefaultTreeModel<DanhMuc> tree,
			final @BindingParam("catSelected") DanhMuc catSelected) {
		if (!catSelected.noId() && catSelected.inUse()) {
			showNotification("Không thể xoá chủ đề đang được sử dụng", "", "error");
			return;
		}

		final List<DanhMuc> checkList = core().getDanhMuc().getDanhMucCon(catSelected);

		for (DanhMuc category : checkList) {
			if (!category.noId() && category.inUse()) {
				showNotification("Không thể xoá chủ đề có chủ đề con đang được sử dụng", "", "error");
				return;
			}
		}

		Messagebox.show("Bạn muốn xóa chủ đề này?", "Xác nhận", Messagebox.CANCEL | Messagebox.OK, Messagebox.QUESTION,
				new EventListener<Event>() {
					@Override
					public void onEvent(final Event event) {
						if (Messagebox.ON_OK.equals(event.getName())) {
							for (DanhMuc category : checkList) {
								category.setTrangThai(core().TT_DA_XOA);
								category.saveNotShowNotification();
							}
							catSelected.setTrangThai(core().TT_DA_XOA);
							catSelected.saveNotShowNotification();
							// ------------
							DefaultTreeNode<DanhMuc> nodeParent = (DefaultTreeNode<DanhMuc>) node1.getParent();
							nodeParent.remove(node1);

							tree.addOpenObject(nodeParent);
							BindUtils.postNotifyChange(null, null, nodeParent, "*");
							BindUtils.postNotifyChange(null, null, node1, "*");

							showNotification("Đã xóa thành công!", "", "success");
						}
					}
				});
	}

	// ========================================================================================================================
	// @command('redirectCatagory', zul='chude/chude.zul',vmArgs=vm,
	// node=node,tree=tree.getModel(),catSelected=vmchild)
	@Command
	public void redirectCatagory(@BindingParam("zul") String zul, @BindingParam("vmArgs") Object vmArgs,
			@BindingParam("node") org.zkoss.zul.DefaultTreeNode<DanhMuc> node1,
			@BindingParam("tree") org.zkoss.zul.DefaultTreeModel<DanhMuc> tree,
			@BindingParam("catSelected") DanhMuc catSelected) {
		Map<String, Object> args = new HashMap<>();
		args.put("node", node1);
		args.put("tree", tree);
		args.put("vmArgs", vmArgs);
		args.put("catSelected", catSelected);
		Executions.createComponents(zul, null, args);
	}

	// =============================================================================================================

	@Transient
	public AbstractValidator getValidatorCatChild() {
		return new AbstractValidator() {

			@Override
			public void validate(final ValidationContext ctx) {
				String value = (String) ctx.getProperty().getValue();
				if (value == null || "".equals(value)) {
					addInvalidMessage(ctx, "error", "Không dược để trống trường này");
				} else {
					JPAQuery<DanhMuc> q = find(DanhMuc.class).where(QDanhMuc.danhMuc.name.eq(value))
							.where(QDanhMuc.danhMuc.trangThai.ne(core().TT_DA_XOA));
					if (getParent() == null) {
						q.where(QDanhMuc.danhMuc.parent.isNull());
					} else {
						q.where(QDanhMuc.danhMuc.parent.eq(getParent()));
					}
					if (!DanhMuc.this.noId()) {
						q.where(QDanhMuc.danhMuc.id.ne(getId()));
					}

					if (q.fetchCount() > 0) {
						addInvalidMessage(ctx, "error", "Tên này đã được sử dụng trong hệ thống");
					}
				}
			}
		};
	}
	
	//====================================================
	
	@Transient
	public List<DanhMuc> getChild(){
		List<DanhMuc> list = new ArrayList<DanhMuc>();
		list.addAll(core().getDanhMuc().getChild(this.getId()).fetch());
		return list;
	}
}