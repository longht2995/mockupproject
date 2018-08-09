package vn.toancauxanh.gg.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.io.FileUtils;
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
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.google.common.base.Strings;

import vn.toancauxanh.model.NhanVien;
import vn.toancauxanh.service.ResizeHinhAnh;

@Entity
@Table(name = " tinbai")
public class TinBai extends Asset<TinBai> {

	private String maDinhDanh = "";
	private Image avatarImage;
	private int soThuTu;
	private String content;
	private String description = "";
	private String alias = "";
	private Date dateBeginTime;
	private Date dateEndTime;
	private boolean publishStatus;
	private int readCount;
	private String title = "";
	private NhanVien author;
	private List<DanhMuc> categories = new ArrayList<DanhMuc>();
	private List<FileEntry> fileEntries = new ArrayList<>();
	public String trangThaiHienThi = "";
	private String img = "/backend/assets/img/edit.png";
	private String hoverImg = "/backend/assets/img/edit_hover.png";
	private String strUpdate = "Thứ tự";
	private boolean updateThanhCong = true;
	private boolean isNoiBat = false;
	private boolean isTieuDiem = false;

	// ======================================================

	public boolean isPublishStatus() {
		return publishStatus;
	}

	public void setMaDinhDanh(String maDinhDanh) {
		String result = "";
		boolean flag = true;
		if (this.getCategories() == null || this.getCategories().isEmpty()) {
			this.maDinhDanh = Strings.nullToEmpty(maDinhDanh);
		} else {
			int size = this.getCategories().get(0).getName().length();
			String s = this.getCategories().get(0).getName();
			char a = ' ';
			for (int i = 0; i < size; i++) {
				if (flag) {
					result += s.charAt(i);
					flag = false;
				}
				if (s.charAt(i) == a) {
					flag = true;
				}
			}
			DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
			String requiredDate = df.format(new Date());
			this.maDinhDanh = unAccent(result.concat(requiredDate)).toUpperCase();
		}

	}

	public String getMaDinhDanh() {
		return maDinhDanh;
	}

	@ManyToOne
	@JoinTable(name = "tinbai_img", joinColumns = { @JoinColumn(name = "tinbai_id") }, inverseJoinColumns = {
			@JoinColumn(name = "img_id") })
	public Image getAvatarImage() {
		return avatarImage;
	}

	public void setAvatarImage(Image avatarImage) {
		this.avatarImage = avatarImage;
	}

	public void setPublishStatus(boolean publishStatus) {
		this.publishStatus = publishStatus;
	}

	public String getTrangThaiHienThi() {
		return trangThaiHienThi;
	}

	public void setTrangThaiHienThi(String trangThaiHienThi) {
		this.trangThaiHienThi = trangThaiHienThi;
	}

	@Transient
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Transient
	public String getHoverImg() {
		return hoverImg;
	}

	public void setHoverImg(String hoverImg) {
		this.hoverImg = hoverImg;
	}

	@Transient
	public String getStrUpdate() {
		return strUpdate;
	}

	public void setStrUpdate(String strUpdate) {
		this.strUpdate = strUpdate;
	}

	@Transient
	public boolean isUpdateThanhCong() {
		return updateThanhCong;
	}

	public void setUpdateThanhCong(boolean updateThanhCong) {
		this.updateThanhCong = updateThanhCong;
	}

	public boolean isNoiBat() {
		return isNoiBat;
	}

	public void setNoiBat(boolean isNoiBat) {
		this.isNoiBat = isNoiBat;
	}

	public boolean isTieuDiem() {
		return isTieuDiem;
	}

	public void setTieuDiem(boolean isTieuDiem) {
		this.isTieuDiem = isTieuDiem;
	}

	public int getSoThuTu() {
		return soThuTu;
	}

	public void setSoThuTu(int soThuTu) {
		this.soThuTu = soThuTu;
	}

	@Column(columnDefinition="TEXT")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = Strings.nullToEmpty(content);
	}

	@Column(length = 750)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = Strings.nullToEmpty(description);
	}

	@Column(length = 1000)
	public String getAlias() {
		if (alias.isEmpty() && "".equals(alias)) {
			alias = unAccent(getTitle());
		}
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = Strings.nullToEmpty(alias);
	}

	public Date getDateBeginTime() {
		return dateBeginTime;
	}

	public void setDateBeginTime(Date dateBeginTime) {
		this.dateBeginTime = dateBeginTime;
	}

	public Date getDateEndTime() {
		return dateEndTime;
	}

	public void setDateEndTime(Date dateEndTime) {
		this.dateEndTime = dateEndTime;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = Strings.nullToEmpty(title);
	}

	@ManyToOne
	public NhanVien getAuthor() {
		return this.author == null ? author = getNhanVien() : this.author;
	}

	public void setAuthor(NhanVien author) {
		this.author = author;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "baiviet_categories", joinColumns = { @JoinColumn(name = "baiviet_id") }, inverseJoinColumns = {
			@JoinColumn(name = "categories_id") })
	public List<DanhMuc> getCategories() {
		return categories;
	}

	public void setCategories(List<DanhMuc> categories) {
		this.categories = categories;
	}

	@Transient
	public DanhMuc getCategory() {
		return categories.isEmpty() ? new DanhMuc() : getCategories().get(0);
	}

	public void setCategory(DanhMuc category1) {
		if (category1 == null) {
			categories.clear();
		} else if (!categories.contains(category1)) {
			categories.clear();
			categories.add(category1);
		}
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tinbai_file", joinColumns = { @JoinColumn(name = "tinbai_id") }, inverseJoinColumns = {
			@JoinColumn(name = "file_id") })
	@Fetch(value = FetchMode.SUBSELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	public List<FileEntry> getFileEntries() {
		return fileEntries;
	}

	public void setFileEntries(List<FileEntry> fileEntries) {
		this.fileEntries = fileEntries;
	}

	@Transient
	public String getNoiBatTieuDiem() {
		return isNoiBat ? "Nổi bật" : "";
	}

	@Transient
	public boolean isShowLuu() {
		boolean show = false;
		show = core().getQuyen().get(core().BAIVIETTHEM) || core().getQuyen().get(core().BAIVIETSUA);
		return show;
	}

	@Transient
	public boolean isShowGui() {
		boolean show = false;
		show = core().getQuyen().get(core().BAIVIETXUATBAN)
				&& (core().getQuyen().get(core().BAIVIETTHEM) || core().getQuyen().get(core().BAIVIETSUA))
				&& (isNew() || isDangSoan());
		return show;
	}

	@Transient
	public boolean isShowXuatBan() {
		boolean show = false;
		show = core().getQuyen().get(core().BAIVIETXUATBAN) && !isPublishStatus();
		return show;
	}

	@Transient
	public boolean isShowKhongXuatBan() {
		boolean show = false;
		show = core().getQuyen().get(core().BAIVIETXUATBAN) && isPublishStatus();
		return show;
	}

	public TinBai() {
	}

	// ==============================================================================================

	@Command
	// @command('saveArticle', list=vmArgs,attr='targetQuery')
	public void saveTinBai(@BindingParam("list") Object list, @BindingParam("attr") String attr,
			@BindingParam("wdn") final Window wdn) {
		try {
			beforeSaveTinBai();
			Image avatarImage = getAvatarImage();
			if (avatarImage != null) {
				if (avatarImage.getImageContent() == null) {
					avatarImage.setTrangThai(Labels.getLabel("da_xoa"));
					avatarImage.saveNotShowNotification();
				} else {
					avatarImage.setArticlesImage(true);
					avatarImage.saveNotShowNotification();
				}
			}

			for (FileEntry file : getFileEntries()) {
				file.saveNotShowNotification();
			}

			if (getAuthor() == null) {
				setAuthor(core().getNhanVien());
			}

			if ("".equals(getTrangThaiSoan())) {
				setTrangThaiSoan(core().TTS_DANG_SOAN);
			}
			save();
			wdn.detach();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		BindUtils.postNotifyChange(null, null, list, attr);
	}

	@Command
	// @command('xuatBan', list=vmArgs, attr='targetQuery',wdn=wdn,stt=true)
	public void xuatBan(@BindingParam("list") Object list, @BindingParam("attr") String attr,
			@BindingParam("wdn") Window wdn, @BindingParam("stt") boolean stt) {

		// set lại trạng thái public
		setPublishStatus(stt);

		// set lại ngày xuất bản
		if (getDateBeginTime() == null) {
			setDateBeginTime(new Date());
		}

		try {
			beforeSaveTinBai();

			Image avatarImage = getAvatarImage();
			if (avatarImage != null) {
				if (avatarImage.getImageContent() == null) {
					avatarImage.setTrangThai(Labels.getLabel("da_xoa"));
					avatarImage.saveNotShowNotification();
				} else {
					avatarImage.setArticlesImage(true);
					avatarImage.saveNotShowNotification();
				}
			}

			for (FileEntry fileEntry : getFileEntries()) {
				fileEntry.saveNotShowNotification();
			}

			if (stt) {
				setTrangThaiSoan(core().TTS_DA_DUYET);
			}

			savePublishStatus(stt);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BindUtils.postNotifyChange(null, null, list, attr);
		if (wdn != null) {
			wdn.detach();
		}
	}

	// @command('copyArticle', vm=item, list=vm, attr='targetQuery')
	@Command
	public void copyTinBai(@BindingParam("vm") TinBai item, @BindingParam("list") Object list,
			@BindingParam("attr") String attr) throws IOException {
		Image imgNew = item.getAvatarImage();
		if (imgNew != null) {
			imgNew.setId(null);
			imgNew.setArticlesImage(true);
			copyImage(imgNew);
			imgNew.save();
			item.setAvatarImage(imgNew);
		}

		List<FileEntry> fileEntries = new ArrayList<FileEntry>();
		for (FileEntry file : item.getFileEntries()) {
			copyFile(file);
			file.setId(null);
			file.save();
			fileEntries.add(file);
		}

		item.setFileEntries(fileEntries);

		List<DanhMuc> catalogs = new ArrayList<DanhMuc>();
		catalogs.add(item.getCategories().get(0));
		item.setCategories(catalogs);
		item.setId(null);
		item.setTrangThai(core().TT_AP_DUNG);
		item.setPublishStatus(false);
		item.setDateBeginTime(null);
		item.setDateEndTime(null);
		item.setTrangThaiSoan(core().TTS_DANG_SOAN);
		item.setAuthor(core().getNhanVien());
		item.setTitle(item.getTitle().concat(" Copy"));
		item.setNgaySua(new Date());
		item.setNgayTao(new Date());
		item.save();

		BindUtils.postNotifyChange(null, null, list, attr);
	}

	protected void copyFile(FileEntry file) throws IOException {
		final long dateTime = new Date().getTime();
		String strUrlSource = "\\home\\hdndhoavangdata".concat(file.getFileUrl());

		final File source = new File(strUrlSource);
		source.getParentFile().mkdirs();

		final String tenFile = file.getName().substring(0, file.getName().lastIndexOf('_')) + "_" + dateTime
				+ file.getName().substring(file.getName().lastIndexOf('.'));

		file.setName(tenFile);
		file.setTepDinhKem(tenFile);
		file.setFileUrl(folderUrl().concat(tenFile));
		file.setNgaySua(new Date());
		file.setNgayTao(new Date());

		final File target = new File(folderStore().concat(tenFile));
		source.getParentFile().mkdirs();

		FileUtils.copyFile(source, target);
	}

	protected void copyImage(Image img1) throws IOException {
		final long dateTime = new Date().getTime();
		String strUrlSource = img1.folderStore().concat(img1.getName());
		final File source = new File(strUrlSource);
		source.getParentFile().mkdirs();

		final String tenFile = img1.getName().substring(0, img1.getName().lastIndexOf('_')) + "_" + dateTime
				+ img1.getName().substring(img1.getName().lastIndexOf('.'));

		img1.setName(tenFile);
		img1.setImageUrl(new Image().folderUrl().concat(tenFile));
		img1.setNgaySua(new Date());
		img1.setNgayTao(new Date());

		final File target = new File(img1.folderStore().concat(tenFile));
		source.getParentFile().mkdirs();

		FileUtils.copyFile(source, target);

	}

	public void beforeSaveTinBai() throws FileNotFoundException, IOException {
		saveImage();
	}

	// lưu hình ảnh
	protected void saveImage() throws IOException {
		Image avatar = getAvatarImage();
		if (avatar != null) {
			org.zkoss.image.Image imageContent = avatar.getImageContent();
			if (imageContent != null) {
				// luu hinh
				avatar.setImageUrl(folderImageUrl().concat(avatar.getName()));
				avatar.setMedium(folderImageUrl().concat("m_" + avatar.getName()));
				avatar.setSmall(folderImageUrl().concat("s_" + avatar.getName()));
				final File baseDir = new File(avatar.folderStore().concat(avatar.getName()));
				Files.copy(baseDir, imageContent.getStreamData());
				ResizeHinhAnh.saveMediumAndSmall2(avatar, avatar.folderStore());
			}
		}
	}

	public String folderImageUrl() {
		return "/" + Labels.getLabel("filestore.folder") + "/image/";
	}

	// =========================================================
	// @command('uploadFile',media=event.media,vmsgs=null)
	@Command
	public void uploadFile(@BindingParam("media") final Media media, @BindingParam("vmsgs") ValidationMessages vmsgs)
			throws IOException {
		if (media.getName().toLowerCase().endsWith(".doc") || media.getName().toLowerCase().endsWith(".docx")
				|| media.getName().toLowerCase().endsWith(".pdf") || media.getName().toLowerCase().endsWith(".xls")
				|| media.getName().toLowerCase().endsWith(".xlsx")) {
			int length = media.getByteData().length;
			if (length > 50000000) {
				showNotification("Chọn file đính kèm có dung lượng nhỏ hơn 50MB.", "", "error");
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
				// getFileEntries().clear();
				getFileEntries().add(entry);
				
				Files.copy(baseDir, media.getStreamData());
				if (vmsgs != null) {
					vmsgs.clearKeyMessages("uploadbtn");
				}

			}
		} else {
			showNotification("Chọn tập tin theo đúng định dạng (*.doc, *.docx, *.xls, *.xlsx, *.pdf)", "", "error");
		}
	}

	@Command
	public void deleteFileDinhKem(@BindingParam("item") final FileEntry file) {
		Messagebox.show("Bạn muốn xóa file đính kèm này?", "Xác nhận", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						if (Messagebox.ON_OK.equals(event.getName())) {
							getFileEntries().remove(file);
							BindUtils.postNotifyChange(null, null, TinBai.this, "fileEntries");
						}

					}
				});
	}

	@Command
	public void downloadFile(@BindingParam("item") final FileEntry e) throws IOException {
		if (!getFileEntries().isEmpty()) {
			final String path = folderStore() + e.getTepDinhKem();
			if (new File(path).exists()) {
				String tenFileRename;
				if (e.getTepDinhKem().lastIndexOf('_') == -1) {
					tenFileRename = e.getTepDinhKem();
				} else {
					tenFileRename = e.getTepDinhKem().substring(0, e.getTepDinhKem().lastIndexOf('_'))
							+ e.getTepDinhKem().substring(e.getTepDinhKem().lastIndexOf('.'));
				}
				if (!"".equals(e.getTepDinhKem())) {
					Filedownload.save(new URL("file://" + path).openStream(), null, tenFileRename);
				}

			} else {
				showNotification("Không tìm thấy tệp tin!", "", "error");
			}
		}
	}

	// =================================================================================

	// onUpload="@command('attachImages',media=event.media)"
	// image="/img/attachment.png"
	@Command
	public void attachImages(@BindingParam("media") final Media media) {
		if (media instanceof org.zkoss.image.Image) {
			if (media.getName().toLowerCase().endsWith(".png") || media.getName().toLowerCase().endsWith(".jpg")) {
				int lengthOfImage = media.getByteData().length;
				if (lengthOfImage > 10000000) {
					showNotification("Chọn hình ảnh có dung lượng nhỏ hơn 10MB.", "", "error");
					return;
				} else {
					String tenFile = media.getName();
					tenFile = tenFile.replace(" ", "");
					tenFile = unAccent(tenFile.substring(0, tenFile.lastIndexOf("."))) + "_"
							+ Calendar.getInstance().getTimeInMillis() + tenFile.substring(tenFile.lastIndexOf("."));

					Image hinhAnh = getAvatarImage();
					if (hinhAnh == null) {
						hinhAnh = new Image();
					}
					setAvatarImage(hinhAnh);
					hinhAnh.setImageContent((org.zkoss.image.Image) media);
					hinhAnh.setName(tenFile);
					BindUtils.postNotifyChange(null, null, this, "avatarImage");
				}
			} else {
				showNotification("Chọn hình ảnh theo đúng định dạng (*.png, *.jpg)", "", "error");
			}
		} else {
			showNotification("File tải lên không phải hình ảnh!", "", "error");
		}
	}

	@Command
	public void deleteImg() {
		setAvatarImage(null);
		BindUtils.postNotifyChange(null, null, this, "avatarImage");
	}

	// =========================================================

	@Command
	public void guiBaiViet(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) {

		if (noId()) {
			saveTinBai(listObject, attr, wdn);
		}
		setTrangThaiSoan(core().TTS_CHO_DUYET);
		saveNotShowNotification();
		BindUtils.postNotifyChange(null, null, listObject, attr);
	}

	// ====================== Validator =======================
	@Command
	public void check(@BindingParam("vmsg") ValidationMessages vm) {
		Date fromDate = getDateBeginTime();
		Date toDate = getDateEndTime();
		if (fromDate != null && toDate != null) {
			if (fromDate.compareTo(toDate) <= 0) {
				vm.clearKeyMessages("lblErr");
			}
		}
		BindUtils.postNotifyChange(null, null, this, "vm");
	}

	@Transient
	public AbstractValidator getValidator() {
		return new AbstractValidator() {

			@Override
			public void validate(ValidationContext ctx) {
				Date fromDate = getDateBeginTime();
				Date toDate = getDateEndTime();

				if (fromDate != null && toDate != null) {
					if (fromDate.compareTo(toDate) > 0) {
						addInvalidMessage(ctx, "lblErr", "Ngày hết hạn phải lớn hơn hoặc bằng ngày xuất bản");
					}
				}
			}
		};
	}

}
