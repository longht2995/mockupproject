package vn.toancauxanh.gg.model;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.sys.ValidationMessages;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.google.common.base.Strings;

@Entity
@Table(name="thongbao")
public class ThongBao extends Asset<ThongBao>{
	private String soKyHieu;
	private String tieuDe = "";
	private Date ngayBanHanh;
	private Date ngayHieuLuc;
	private FileEntry file;
	private String nguoiKy;
	private CoQuanBanHanh coQuanBanHanh;
	private boolean xuatBan;
	public Date getNgayHieuLuc() {
		return ngayHieuLuc;
	}
	public void setNgayHieuLuc(Date ngayHieuLuc) {
		this.ngayHieuLuc = ngayHieuLuc;
	}
	public String getNguoiKy() {
		return nguoiKy;
	}
	public void setNguoiKy(String nguoiKy) {
		this.nguoiKy = nguoiKy;
	}
	public String getSoKyHieu() {
		return soKyHieu;
	}
	public void setSoKyHieu(String soKyHieu) {
		this.soKyHieu = soKyHieu;
	}
	public String getTieuDe() {
		return tieuDe;
	}
	public void setTieuDe(String tieuDe) {
		this.tieuDe = tieuDe;
	}
	public Date getNgayBanHanh() {
		return ngayBanHanh;
	}
	public void setNgayBanHanh(Date ngayBanHanh) {
		this.ngayBanHanh = ngayBanHanh;
	}
	@ManyToOne
	public FileEntry getFile() {
		return file;
	}
	public void setFile(FileEntry file) {
		this.file = file;
	}
	@ManyToOne
	@JoinTable(name="thongbao_coquan",
            joinColumns={@JoinColumn(name="thongbao_id")},
            inverseJoinColumns={@JoinColumn(name="coquan_id")})
	public CoQuanBanHanh getCoQuanBanHanh() {
		return coQuanBanHanh;
	}
	public void setCoQuanBanHanh(CoQuanBanHanh coQuanBanHanh) {
		this.coQuanBanHanh = coQuanBanHanh;
	}
	public boolean isXuatBan() {
		return xuatBan;
	}
	public void setXuatBan(boolean xuatBan) {
		this.xuatBan = xuatBan;
	}
	@Command
	public void saveThongBao(@BindingParam("list") final Object listObject,
			@BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) {
		if ("".equals(getTrangThaiSoan())) {
			setTrangThaiSoan(core().TTS_DANG_SOAN);
		}
		if(file!=null) {
			file.saveNotShowNotification();
		}
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);
	}
	@Command
	public void xuatBan(@BindingParam("list") final Object listObject,
			@BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn,
			@BindingParam("stt") final boolean stt) {
		if (stt) {
			setTrangThaiSoan(core().TTS_DA_DUYET);
		}
		setXuatBan(stt);
		if(file!=null) {
			file.saveNotShowNotification();
		}
		
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);
	}
	@Command
	public void uploadFile(@BindingParam("media") final Media media,
			@BindingParam("vmsgs")  ValidationMessages vmsgs)
			throws IOException {
		if (media.getName().toLowerCase().endsWith(".doc")
				|| media.getName().toLowerCase().endsWith(".docx")
				|| media.getName().toLowerCase().endsWith(".pdf")
				|| media.getName().toLowerCase().endsWith(".xls")
				|| media.getName().toLowerCase().endsWith(".xlsx")) {
			int length = media.getByteData().length;
			if (length > 50000000) {
		        showNotification("Chọn file đính kèm có dung lượng nhỏ hơn 50MB.", "", "error");
		        return;
			}
			else{
				final long dateTime = new Date().getTime();
				final String tenFile = unAccent(media.getName().substring(0,media.getName().lastIndexOf('.')))
						+ "_"
						+ dateTime
						+ media.getName().substring(media.getName().lastIndexOf('.'));
				final String filePathDoc = folderStore() + tenFile;
				final File baseDir = new File(filePathDoc);
				baseDir.getParentFile().mkdirs();
				file = new FileEntry();
				file.setName(tenFile);
				file.setExtension(getExtension(Strings.nullToEmpty(media.getName())));
				file.setFileUrl(folderUrl() + tenFile);
				file.setTepDinhKem(tenFile);
				file.setTenHienThi(file.getTenFileDinhKem());
				Files.copy(baseDir, media.getStreamData());
				if (vmsgs != null) {
					vmsgs.clearKeyMessages("uploadbtn");
				}
				BindUtils.postNotifyChange(null, null, this, "file");
				BindUtils.postNotifyChange(null, null, vmsgs, "*");
				showNotification("Tải tập tin thành công!", "", "success");
			}
		} else {
			showNotification("Chọn tập tin theo đúng định dạng (*.doc, *.docx, *.xls, *.xlsx, *.pdf)", "", "error");
		}
	}
	@Command
	public void deleteFileDinhKem(@BindingParam("item") final FileEntry e) {
		Messagebox.show("Bạn muốn xóa file đính kèm này?", "Xác nhận",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener<Event>() {
					@Override
					public void onEvent(final Event event) {
						if (Messagebox.ON_OK.equals(event.getName())) {
							file = null;
							BindUtils.postNotifyChange(null, null,
									ThongBao.this, "file");
						}
					}
				});
	}
	
}
