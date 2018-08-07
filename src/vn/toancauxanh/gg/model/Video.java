package vn.toancauxanh.gg.model;

import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.sys.ValidationMessages;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.google.common.base.Strings;

import vn.toancauxanh.service.ResizeHinhAnh;

@Entity
@Table(name="video")
public class Video extends Asset<Video>{
	
	private String tieuDe = "";
	
	private String moTa="";
	
	private Image image;
	
	private Date ngayXuatBan;
	
	private Date ngayHetHan;
	
	private FileEntry file = new FileEntry();
	
	private boolean xuatBan;
		
	private ChuDeVideo chuDeVideo;
	
	private String txtLoai = "0";
	
	private String alias = "";
	
	public boolean isXuatBan() {
		return xuatBan;
	}

	public void setXuatBan(boolean xuatBan) {
		this.xuatBan = xuatBan;
	}

	public String getTxtLoai() {
		return txtLoai;
	}
	
	@NotifyChange("txtLoai")
	public void setTxtLoai(String txtLoai) {
		this.txtLoai = txtLoai;
		if(file==null) {
			file = new FileEntry();
		}
		BindUtils.postNotifyChange(null, null, this, "txtLoai");
	}
	
	@ManyToOne
	public ChuDeVideo getChuDeVideo() {
		return chuDeVideo;
	}
	
	@ManyToOne
	public FileEntry getFile() {
		return file;
	}
	
	public void setFile(FileEntry file) {
		this.file = file;
	}
	
	public void setChuDeVideo(ChuDeVideo chuDeVideo) {
		this.chuDeVideo = chuDeVideo;
	}
	
	@ManyToOne
	public Image getImage() {
		return image;
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
	
	public Date getNgayXuatBan() {
		return ngayXuatBan;
	}
	public void setNgayXuatBan(Date ngayXuatBan) {
		this.ngayXuatBan = ngayXuatBan;
	}
	
	public Date getNgayHetHan() {
		return ngayHetHan;
	}
	public void setNgayHetHan(Date ngayHetHan) {
		this.ngayHetHan = ngayHetHan;
	}
	
	public String getTieuDe() {
		return tieuDe;
	}
	
	public void setTieuDe(String tieuDe) {
		this.tieuDe = tieuDe;
	}
	
	public String getMoTa() {
		return moTa;
	}
	
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Command
	public void saveVideo(@BindingParam("list") final Object listObject,
			@BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) throws IOException {
		if ("".equals(getTrangThaiSoan())) {
			setTrangThaiSoan(core().TTS_DANG_SOAN);
		}
		if(image!=null) {
			saveImage();
		}
		Image avatarImage2 = getImage();
		if (avatarImage2 != null) {
			if (avatarImage2.getImageContent() == null) {
				avatarImage2.setTrangThai(Labels.getLabel("da_xoa"));
				avatarImage2.saveNotShowNotification();
			} else {
				avatarImage2.setArticlesImage(true);
				avatarImage2.saveNotShowNotification();
			}
		}
		if(file!=null) {
			file.saveNotShowNotification();
		}
		setAlias(getAlias(getTieuDe()).toLowerCase());
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);
	}
	public String getAlias(String s) {
		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("").replace('đ','d').replace('Đ','D').replace(' ', '-');
	}
	@Command
	public void xuatBan(@BindingParam("list") final Object listObject,
			@BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn,
			@BindingParam("stt") final boolean stt) throws IOException {
		if (stt) {
			setTrangThaiSoan(core().TTS_DA_DUYET);
		}else {
			setTrangThaiSoan(core().TTS_DANG_SOAN);
		}
		setXuatBan(stt);
		if ("".equals(getTrangThaiSoan())) {
			setTrangThaiSoan(core().TTS_DANG_SOAN);
		}
		if(image!=null) {
			saveImage();
		}
		Image avatarImage2 = getImage();
		if (avatarImage2 != null) {
			if (avatarImage2.getImageContent() == null) {
				avatarImage2.setTrangThai(Labels.getLabel("da_xoa"));
				avatarImage2.saveNotShowNotification();
			} else {
				avatarImage2.setArticlesImage(true);
				avatarImage2.saveNotShowNotification();
			}
		}
		if(file!=null) {
			file.saveNotShowNotification();
		}
		setAlias(getAlias(getTieuDe()).toLowerCase());
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);
	}
	@Command
	public void attachImages(@BindingParam("media") final Media media) {
		if (media instanceof org.zkoss.image.Image) {
			if(media.getName().toLowerCase().endsWith(".png")
				|| media.getName().toLowerCase().endsWith(".jpg")){
				int lengthOfImage = media.getByteData().length;
				if (lengthOfImage > 10000000) {
			        showNotification("Chọn hình ảnh có dung lượng nhỏ hơn 10MB.", "", "error");
			        return;
				}
				else {
					String tenFile = media.getName();
					tenFile = tenFile.replace(" ", "");
					tenFile = unAccent(tenFile.substring(0, tenFile.lastIndexOf("."))) + "_"
							+ Calendar.getInstance().getTimeInMillis()
							+ tenFile.substring(tenFile.lastIndexOf("."));
					
					Image img2 = getImage();
					if (img2 == null) {
						img2 = new Image();
					}
					setImage(img2);
					image.setImageContent((org.zkoss.image.Image) media);
					img2.setName(tenFile);
					BindUtils.postNotifyChange(null, null, this, "image");
				}
			}
			else {
				showNotification("Chọn hình ảnh theo đúng định dạng (*.png, *.jpg)","","error");
			}
		} else {
			showNotification("File tải lên không phải hình ảnh!", "", "error");
		}
	}
	
	@Transient
	protected void saveImage() throws IOException {
		org.zkoss.image.Image imageContent2 = image.getImageContent();
		if (imageContent2 != null) {
			image.setImageUrl(folderUrl().concat(image.getName()));
			image.setMedium(folderUrl().concat("m_" + image.getName()));
			image.setSmall(folderUrl().concat("s_" + image.getName()));
			final File baseDir = new File(folderStore().concat(image.getName()));
			Files.copy(baseDir, imageContent2.getStreamData());
			ResizeHinhAnh.saveMediumAndSmall2(image,
					folderStore());
		}
	}
	
	@Command
	public void deleteImg() {
		image.setImageContent(null);
		image.setName("");
		image.setFlagSetImage(false);
		BindUtils.postNotifyChange(null, null, this, "image");
	}
	@Transient
	public String folderImageUrl() {
		return "/" + Labels.getLabel("filestore.folder")+"/";
	}
	@Command
	public void uploadFile(@BindingParam("media") final Media media,
			@BindingParam("vmsgs")  ValidationMessages vmsgs)
			throws IOException {
		if (media.getName().toLowerCase().endsWith(".mp4")
				|| media.getName().toLowerCase().endsWith(".ogm")
				|| media.getName().toLowerCase().endsWith(".avi")
				|| media.getName().toLowerCase().endsWith(".mpg")
				|| media.getName().toLowerCase().endsWith(".wmv")) {
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
					vmsgs.clearKeyMessages("lienket");
				}
				
				BindUtils.postNotifyChange(null, null, this, "file");
				BindUtils.postNotifyChange(null, null, vmsgs, "*");
				showNotification("Tải tập tin thành công!", "", "success");
			}
		} else {
			showNotification("Chọn tập tin theo đúng định dạng (*.mp4, *.ogm, *.avi, *.mpg, *.wmv)", "", "error");
		}
	}

	@Command
	public void deleteFileDinhKem(@BindingParam("item") final FileEntry e) {
		Messagebox.show("Bạn muốn xóa file đính kèm này?", "Xác nhận", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener<Event>() {
					@Override
					public void onEvent(final Event event) {
						if (Messagebox.ON_OK.equals(event.getName())) {
							file = new FileEntry();
							BindUtils.postNotifyChange(null, null, Video.this, "file");
						}
					}
				});
	}
	@Transient
	public AbstractValidator getValidator() {
		return new AbstractValidator() {
			@Override
			public void validate(final  ValidationContext ctx) {			
				Date fromDate = getNgayXuatBan();
				Date toDate = getNgayHetHan();
				FileEntry file = getFile();
				if(txtLoai.equals("0")) {
					if("".equals(file.getTenFileDinhKem())) {
						addInvalidMessage(ctx, "uploadbtn",
								"Bạn chưa upload video.");
					}
				}
				if(txtLoai.equals("1")) {
					if("".equals(file.getFileUrl())) {
						addInvalidMessage(ctx, "lienket",
								"Bạn chưa nhập liên kết video");
					}
				}
				if (fromDate != null && toDate != null) {
					if (fromDate.compareTo(toDate) > 0) {
						addInvalidMessage(ctx, "dateErr",
								"Ngày hết hạn phải lớn hơn hoặc bằng ngày xuất bản.");
					}
				}
				
			}
		};
	}
	@Transient
	public boolean checkId() {
		if(getId()==null || getId()==0) {
			return false;
		}return true;
	}
}
