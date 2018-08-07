package vn.toancauxanh.gg.model;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.sys.ValidationMessages;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.google.common.base.Strings;

import vn.toancauxanh.model.NhanVien;

@Entity
@Table(name = "hoidap")
public class HoiDap extends Asset<HoiDap> {

	private String noiDung = "";
	private String tieuDe = "";
	private LinhVucHoiDap linhVuc;
	private String nguoiHoi = "";
	private String soDienThoai = "";
	private String diaChi = "";

	private Date ngayPhanAnh;
	private Date ngayTraLoi;

	private boolean xuatBan = false;
	private String email = "";
	private NhanVien daiBieuGiamSat;
	private String cauTraLoi = "";
	private List<FileEntry> fileCauHoi = new ArrayList<FileEntry>();
	private List<FileEntry> fileTraLoi = new ArrayList<FileEntry>();

	public HoiDap() {
	}

	public boolean isXuatBan() {
		return xuatBan;
	}

	public void setXuatBan(boolean xuatBan) {
		this.xuatBan = xuatBan;
	}

	public String getNoiDung() {
		return noiDung;
	}

	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}

	public String getTieuDe() {
		return tieuDe;
	}

	public void setTieuDe(String tieuDe) {
		this.tieuDe = tieuDe;
	}

	@ManyToOne
	public LinhVucHoiDap getLinhVuc() {
		return linhVuc;
	}

	public void setLinhVuc(LinhVucHoiDap linhVuc) {
		this.linhVuc = linhVuc;
	}

	public String getNguoiHoi() {
		return nguoiHoi;
	}

	public void setNguoiHoi(String nguoiHoi) {
		this.nguoiHoi = nguoiHoi;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = Strings.nullToEmpty(soDienThoai);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getNgayPhanAnh() {
		return ngayPhanAnh;
	}

	public void setNgayPhanAnh(Date ngayPhanAnh) {
		this.ngayPhanAnh = ngayPhanAnh;
	}

	public Date getNgayTraLoi() {
		return ngayTraLoi;
	}

	public void setNgayTraLoi(Date ngayTraLoi) {
		this.ngayTraLoi = ngayTraLoi;
	}

	@ManyToOne
	public NhanVien getDaiBieuGiamSat() {
		return daiBieuGiamSat;
	}

	public void setDaiBieuGiamSat(NhanVien daiBieuGiamSat) {
		this.daiBieuGiamSat = daiBieuGiamSat;
	}

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "hoidap_filecauhoi", joinColumns = { @JoinColumn(name = "hoidap_id") }, inverseJoinColumns = {
			@JoinColumn(name = "filecauhoi_id") })
	@Fetch(value = FetchMode.SUBSELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	public List<FileEntry> getFileCauHoi() {
		return fileCauHoi;
	}

	public void setFileCauHoi(List<FileEntry> fileCauHoi) {
		this.fileCauHoi = fileCauHoi;
	}

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "hoidap_filetraloi", joinColumns = { @JoinColumn(name = "hoidap_id") }, inverseJoinColumns = {
			@JoinColumn(name = "filetraloi_id", nullable = true) })
	@Fetch(value = FetchMode.SUBSELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	public List<FileEntry> getFileTraLoi() {
		return fileTraLoi;
	}

	public void setFileTraLoi(List<FileEntry> fileTraLoi) {
		this.fileTraLoi = fileTraLoi;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getCauTraLoi() {
		return cauTraLoi;
	}

	public void setCauTraLoi(String cauTraLoi) {
		this.cauTraLoi = cauTraLoi;
	}

	@Transient
	public boolean isShowLuu() {
		boolean show = false;
		show = core().getQuyen().get(core().HOIDAPTHEM) || core().getQuyen().get(core().HOIDAPSUA);
		return show;
	}

	@Transient
	public boolean isShowXuatBan() {
		boolean show = false;
		show = core().getQuyen().get(core().HOIDAPXUATBAN) && !isXuatBan();
		return show;
	}

	@Transient
	public boolean isShowKhongXuatBan() {
		boolean show = false;
		show = core().getQuyen().get(core().BAIVIETXUATBAN) && isXuatBan();
		return show;
	}

	// ================================================================================

	// @command('saveHoiDap',list=vmArgs, wdn=wdn, attr='targetQuery')
	@Command
	public void setCheckXuatBan(@BindingParam("list") final Object list, @BindingParam("attr") final String attr) {
		if (isXuatBan() == true && (getCauTraLoi() == null || getCauTraLoi().isEmpty())) {
			showNotification("Bạn phải trả lời trước", "", "error");
			setXuatBan(false);
			return;
		}
		savePublishStatus(!isXuatBan());
		BindUtils.postNotifyChange(null, null, list, attr);

	}

	@Command
	public void saveHoiDap(@BindingParam("list") final Object list, @BindingParam("wdn") final Window wdn,
			@BindingParam("attr") final String attr) {
		setNgayPhanAnh(new Date());
		for (FileEntry file : getFileCauHoi()) {
			file.saveNotShowNotification();
		}
		for (FileEntry file : getFileTraLoi()) {
			file.saveNotShowNotification();
		}

		if (getCauTraLoi() != null && !getCauTraLoi().isEmpty()) {
			setTrangThaiTraLoi(core().TT_DA_TRA_LOI);
		} else {
			setTrangThaiTraLoi(core().TT_CHUA_TRA_LOI);
		}
		save();
		BindUtils.postNotifyChange(null, null, list, attr);
		wdn.detach();
	}

	// @command('saveHoiDap',list=vmArgs, wdn=wdn, attr='targetQuery', stt =
	// true)
	@Command
	public void xuatBan(@BindingParam("list") final Object list, @BindingParam("wdn") final Window wdn,
			@BindingParam("attr") final String attr, @BindingParam("stt") final boolean stt) {
		if (stt && (getCauTraLoi() == null || getCauTraLoi().isEmpty())) {
			showNotification("Bạn phải trả lời trước", "", "error");
			return;
		} else {
			setXuatBan(true);
		}
		for (FileEntry file : getFileCauHoi()) {
			file.saveNotShowNotification();
		}
		for (FileEntry file : getFileTraLoi()) {
			file.saveNotShowNotification();
		}

		if (getCauTraLoi() != null && !getCauTraLoi().isEmpty()) {
			setTrangThaiTraLoi(core().TT_DA_TRA_LOI);
		} else {
			setTrangThaiTraLoi(core().TT_CHUA_TRA_LOI);
		}
		save();
		BindUtils.postNotifyChange(null, null, list, attr);
		if (isXuatBan()) {
			wdn.detach();
		}

	}

	// @command('uploadFile',media=event.media,vmsgs=null)
	@Command
	public void uploadFile(@BindingParam("media") final Media media, @BindingParam("vmsgs") ValidationMessages vmsgs,
			@BindingParam("check") final int check) throws IOException {
		if (media.getName().toLowerCase().endsWith(".doc") || media.getName().toLowerCase().endsWith(".docx")
				|| media.getName().toLowerCase().endsWith(".pdf") || media.getName().toLowerCase().endsWith(".xls")
				|| media.getName().toLowerCase().endsWith(".xlsx")) {
			int length = media.getByteData().length;
			if (length > 50000000) {
				showNotification("Chọn file đính kèm có dung lượng nhỏ hơn 50MB", "", "error");
				return;
			} else {
				final long dateTime = new Date().getTime();
				final String tenFile = unAccent(media.getName().substring(0, media.getName().lastIndexOf('.'))) + "_"
						+ dateTime + media.getName().substring(media.getName().lastIndexOf('.'));

				final String filePathDoc = folderStore() + tenFile;
				final File baseDir = new File(filePathDoc);
				baseDir.getParentFile().mkdirs();

				FileEntry entry = new FileEntry();
				entry.setName(tenFile);
				entry.setExtension(getExtension(Strings.nullToEmpty(media.getName())));
				entry.setFileUrl(folderUrl() + tenFile);
				entry.setTepDinhKem(tenFile);
				entry.setTenHienThi(entry.getTenFileDinhKem());

				if (check == 1) {
					getFileCauHoi().add(entry);
				} else if (check == 2) {
					getFileTraLoi().add(entry);
				}

				Files.copy(baseDir, media.getStreamData());
				if (vmsgs != null) {
					vmsgs.clearKeyMessages("uploadbtn");
				}

				if (check == 1) {
					BindUtils.postNotifyChange(null, null, this, "fileCauHoi");
					BindUtils.postNotifyChange(null, null, vmsgs, "*");
					showNotification("Tải tập tin thành công!", "", "success");
				} else if (check == 2) {
					BindUtils.postNotifyChange(null, null, this, "fileTraLoi");
					BindUtils.postNotifyChange(null, null, vmsgs, "*");
					showNotification("Tải tập tin thành công!", "", "success");
				}
			}
		} else {
			showNotification("Chọn tập tin theo đúng định dạng (*.doc, *.docx, *.xls, *.xlsx, *.pdf)", "", "error");
		}
	}

	// @command('downloadFile', item=each, check = 1)
	@Command
	public void downloadFile(@BindingParam("item") final FileEntry file, @BindingParam("check") final int check)
			throws MalformedURLException, IOException {
		if (check == 1) {
			if (!getFileCauHoi().isEmpty()) {
				final String path = folderStore() + file.getTepDinhKem();
				if (new File(path).exists()) {
					String tenFileRename;
					if (file.getTepDinhKem().lastIndexOf('_') == -1) {
						tenFileRename = file.getTepDinhKem();
					} else {
						tenFileRename = file.getTepDinhKem().substring(0, file.getTepDinhKem().lastIndexOf('_'))
								+ file.getTepDinhKem().substring(file.getTepDinhKem().lastIndexOf('.'));
					}
					if (!"".equals(file.getTepDinhKem())) {
						Filedownload.save(new URL("file://" + path).openStream(), null, tenFileRename);
					}
				}
			} else {
				showNotification("Không thể tìm kiếm tệp tin", "", "error");
			}
		} else if (check == 2) {
			if (!getFileTraLoi().isEmpty()) {
				final String path = folderStore() + file.getTepDinhKem();
				if (new File(path).exists()) {
					String tenFileRename;
					if (file.getTepDinhKem().lastIndexOf('_') == -1) {
						tenFileRename = file.getTepDinhKem();
					} else {
						tenFileRename = file.getTepDinhKem().substring(0, file.getTepDinhKem().lastIndexOf('_'))
								+ file.getTepDinhKem().substring(file.getTepDinhKem().lastIndexOf('.'));
					}
					if (!"".equals(file.getTepDinhKem())) {
						Filedownload.save(new URL("file://" + path).openStream(), null, tenFileRename);
					}
				}
			} else {
				showNotification("Không thể tìm kiếm tệp tin", "", "error");
			}
		}
	}

	// @command('deleteFileDinhKem', item=each, check = 1)
	@Command
	public void deleteFileDinhKem(@BindingParam("item") final FileEntry file, @BindingParam("check") final int check) {
		Messagebox.show("Bạn muốn xóa file đính kèm này?", "Xác nhận", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						if (Messagebox.ON_OK.equals(event.getName())) {
							if (check == 1) {
								getFileCauHoi().remove(file);
								BindUtils.postNotifyChange(null, null, HoiDap.this, "fileCauHoi");
							} else if (check == 2) {
								getFileTraLoi().remove(file);
								BindUtils.postNotifyChange(null, null, HoiDap.this, "fileTraLoi");
							}
						}

					}
				});
	}

	// ===================================================

	@Transient
	public AbstractValidator getValidator() {
		return new AbstractValidator() {

			@Override
			public void validate(ValidationContext ctx) {
				Date fromDate = getNgayTao();
				Date toDate = getNgaySua();

				if (fromDate != null && toDate != null) {
					if (fromDate.compareTo(toDate) > 0) {
						addInvalidMessage(ctx, "lblErr", "Ngày trả lời phải lớn hơn hoặc bằng ngày phản ánh");
					}
				}
			}
		};
	}

}
