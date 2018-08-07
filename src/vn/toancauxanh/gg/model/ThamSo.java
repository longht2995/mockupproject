package vn.toancauxanh.gg.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Window;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.enums.ThamSoEnum;
import vn.toancauxanh.model.Model;

@Entity
@Table(name = "thamso")
public class ThamSo extends Model<ThamSo> {

	private ThamSoEnum ma;
	private Long value;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	@Enumerated(EnumType.STRING)
	public ThamSoEnum getMa() {
		return ma;
	}

	public void setMa(ThamSoEnum ma) {
		this.ma = ma;
	}

	// ===============================================

	@Command
	public void saveThamSo(@BindingParam("obj") final DanhMuc objDanhMuc, @BindingParam("objTS") final ThamSo objThamSo,
			@BindingParam("vm") final Object vm) {
		if (objDanhMuc != null && objThamSo != null) {
			if (kiemTraTonTai(objDanhMuc.getId())) {
				swap(objThamSo, objDanhMuc.getId());
				BindUtils.postNotifyChange(null, null, vm, "*");
				return;
			}
			objThamSo.setValue(objDanhMuc.getId());
			objThamSo.save();
		}
		BindUtils.postNotifyChange(null, null, vm, "*");
	}

	public boolean kiemTraTonTai(Long id) {
		JPAQuery<ThamSo> q = find(ThamSo.class).clone().where(QThamSo.thamSo.trangThai.ne(core().TT_DA_XOA))
				.where(QThamSo.thamSo.value.eq(id));
		return q.fetchCount() > 0 ? true : false;
	}

	public void swap(ThamSo obj, Long id) {
		ThamSo objThamSo = find(ThamSo.class).where(QThamSo.thamSo.trangThai.ne(core().TT_DA_XOA))
				.where(QThamSo.thamSo.value.eq(id)).fetchFirst();
		if (obj.getValue() == null) {
			objThamSo.setValue(null);
			obj.setValue(id);
			obj.save();
			objThamSo.save();
			return;
		}
		objThamSo.setValue(obj.getValue());
		obj.setValue(id);
		obj.save();
		objThamSo.save();
	}

	// =====================================================================

	@Command
	public void saveTieuDe(@BindingParam("list") final Object list, @BindingParam("wdn") final Window wdn) {
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, list, "*");
	}

}
