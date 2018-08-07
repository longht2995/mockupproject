package vn.toancauxanh.gg.model;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.sys.ValidationMessages;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.google.common.base.Strings;

@Entity
@Table(name="vanban")
public class VanBan extends Asset<VanBan>{
	private String soKyHieu;
	private String trichYeu;
	private LoaiVanBan loaiVanBan;
	private FileEntry file;
	private String moTa;
	private boolean xuatBan;
	private LinhVucVanBan linhVucVanBan;
	private CoQuanBanHanh coQuanBanHanh;
	private String nguoiKy;
	private Date ngayBanHanh;
	private Date ngayHieuLuc;
	private CapBanHanh capBanHanh;
	@ManyToOne
	@JoinTable(name="vanban_capbanhanh",
            joinColumns={@JoinColumn(name="vanban_id")},
            inverseJoinColumns={@JoinColumn(name="capbanhanh_id")})
	public CapBanHanh getCapBanHanh() {
		return capBanHanh;
	}
	public void setCapBanHanh(CapBanHanh capBanHanh) {
		this.capBanHanh = capBanHanh;
	}
	public Date getNgayHieuLuc() {
		return ngayHieuLuc;
	}
	public void setNgayHieuLuc(Date ngayHieuLuc) {
		this.ngayHieuLuc = ngayHieuLuc;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	public boolean isXuatBan() {
		return xuatBan;
	}
	public void setXuatBan(boolean xuatBan) {
		this.xuatBan = xuatBan;
	}
	public String getNguoiKy() {
		return nguoiKy;
	}
	public void setNguoiKy(String nguoiKy) {
		this.nguoiKy = nguoiKy;
	}
	public Date getNgayBanHanh() {
		return ngayBanHanh;
	}
	
	public void setNgayBanHanh(Date ngayBanHanh) {
		this.ngayBanHanh = ngayBanHanh;
	}
	@Transient
	public boolean checkId() {
		if(getId()==null || getId()==0) {
			return false;
		}return true;
	}
	@ManyToOne
	@JoinTable(name="vanban_linhvuc",
            joinColumns={@JoinColumn(name="vanban_id")},
            inverseJoinColumns={@JoinColumn(name="linhvuc_id")})
	public LinhVucVanBan getLinhVucVanBan() {
		return linhVucVanBan;
	}
	public void setLinhVucVanBan(LinhVucVanBan linhVucVanBan) {
		this.linhVucVanBan = linhVucVanBan;
	}
	@ManyToOne
	@JoinTable(name="vanban_coquan",
            joinColumns={@JoinColumn(name="vanban_id")},
            inverseJoinColumns={@JoinColumn(name="coquan_id")})
	public CoQuanBanHanh getCoQuanBanHanh() {
		return coQuanBanHanh;
	}
	public void setCoQuanBanHanh(CoQuanBanHanh coQuanBanHanh) {
		this.coQuanBanHanh = coQuanBanHanh;
	}
	@ManyToOne
	public FileEntry getFile() {
		return file;
	}
	public void setFile(FileEntry file) {
		this.file = file;
	}
	public String getSoKyHieu() {
		return soKyHieu;
	}
	public void setSoKyHieu(String soKyHieu) {
		this.soKyHieu = soKyHieu;
	}
	public String getTrichYeu() {
		return trichYeu;
	}
	public void setTrichYeu(String trichYeu) {
		this.trichYeu = trichYeu;
	}
	@ManyToOne
	@JoinTable(name="vanban_loaivanban",
            joinColumns={@JoinColumn(name="vanban_id")},
            inverseJoinColumns={@JoinColumn(name="loaivanban_id")})
	public LoaiVanBan getLoaiVanBan() {
		return loaiVanBan;
	}
	public void setLoaiVanBan(LoaiVanBan loaiVanBan) {
		this.loaiVanBan = loaiVanBan;
	}
	@Command
	public void saveVanBan(@BindingParam("list") final Object listObject,
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
									VanBan.this, "file");
						}
					}
				});
	}
	@Command
	public void resetMess(@BindingParam("vmsgs")  ValidationMessages vmsgs) {
		if (vmsgs != null) {
			if(getNgayBanHanh().compareTo(getNgayHieuLuc())<=0) {
				vmsgs.clearKeyMessages("dateErr");
			}
		}
		BindUtils.postNotifyChange(null, null, vmsgs, "*");
		
	}
	@Transient
	public AbstractValidator getValidator() {
		return new AbstractValidator() {
			@Override
			public void validate(final  ValidationContext ctx) {
				/*FileEntry file = getFile();
				if("".equals(file.getFileUrl())) {
					addInvalidMessage(ctx, "uploadbtn",
							"Bạn chưa upload file đính kèm.");
				}*/
				if(file==null) {
					addInvalidMessage(ctx, "uploadbtn",
							"Bạn chưa upload file đính kèm.");
				}
				Date fromDate = getNgayBanHanh();
				Date toDate = getNgayHieuLuc();
				if (fromDate != null && toDate != null) {
					if (fromDate.compareTo(toDate) > 0) {
						addInvalidMessage(ctx, "dateErr",
								"Ngày hiệu lực phải lớn hơn hoặc bằng ngày ban hành.");
					}
				}
				
			}
		};
	}
	
	@Command
	public void downloadFile(@BindingParam("item") final FileEntry e)
			throws IOException {
		//System.out.println("BaiViet downloadFile");
		if (getFile() !=null) {
			final String path = folderStore() + e.getTepDinhKem();
			if (new File(path).exists()) {
				String tenFileRename;
				if (e.getTepDinhKem().lastIndexOf('_') == -1) {
					tenFileRename = e.getTepDinhKem();
				} else {
					tenFileRename = e.getTepDinhKem().substring(0,
							e.getTepDinhKem().lastIndexOf('_'))
							+ e.getTepDinhKem().substring(
									e.getTepDinhKem().lastIndexOf('.'));
				}
				if (!"".equals(e.getTepDinhKem())) {
					Filedownload.save(new URL("file:///" + path).openStream(),null, tenFileRename);
					//System.out.println("FileDownload save: file:///" + path);
					//System.out.println("user-agent: " + Executions.getCurrent().getHeader("user-agent"));					
				}

			} else {
				showNotification("Không tìm thấy tệp tin!", "", "error");
			}
		}
	}
}
