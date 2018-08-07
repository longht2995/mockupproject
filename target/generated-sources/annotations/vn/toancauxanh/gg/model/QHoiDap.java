package vn.toancauxanh.gg.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHoiDap is a Querydsl query type for HoiDap
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QHoiDap extends EntityPathBase<HoiDap> {

    private static final long serialVersionUID = -1517148558L;

    private static final PathInits INITS = new PathInits("*", "nguoiSua.*.*.*.*", "nguoiTao.*.*.*.*");

    public static final QHoiDap hoiDap = new QHoiDap("hoiDap");

    public final QAsset _super;

    public final StringPath cauTraLoi = createString("cauTraLoi");

    //inherited
    public final BooleanPath ckEditorPopup;

    public final vn.toancauxanh.model.QNhanVien daiBieuGiamSat;

    //inherited
    public final BooleanPath daXoa;

    public final StringPath diaChi = createString("diaChi");

    public final StringPath email = createString("email");

    public final ListPath<FileEntry, QFileEntry> fileCauHoi = this.<FileEntry, QFileEntry>createList("fileCauHoi", FileEntry.class, QFileEntry.class, PathInits.DIRECT2);

    public final ListPath<FileEntry, QFileEntry> fileTraLoi = this.<FileEntry, QFileEntry>createList("fileTraLoi", FileEntry.class, QFileEntry.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Long> id;

    public final QLinhVucHoiDap linhVuc;

    public final DateTimePath<java.util.Date> ngayPhanAnh = createDateTime("ngayPhanAnh", java.util.Date.class);

    //inherited
    public final DateTimePath<java.util.Date> ngaySua;

    //inherited
    public final DateTimePath<java.util.Date> ngayTao;

    public final DateTimePath<java.util.Date> ngayTraLoi = createDateTime("ngayTraLoi", java.util.Date.class);

    public final StringPath nguoiHoi = createString("nguoiHoi");

    // inherited
    public final vn.toancauxanh.model.QNhanVien nguoiSua;

    // inherited
    public final vn.toancauxanh.model.QNhanVien nguoiTao;

    public final StringPath noiDung = createString("noiDung");

    public final StringPath soDienThoai = createString("soDienThoai");

    public final StringPath tieuDe = createString("tieuDe");

    //inherited
    public final StringPath trangThai;

    //inherited
    public final StringPath trangThaiSoan;

    //inherited
    public final StringPath trangThaiTraLoi;

    public final BooleanPath xuatBan = createBoolean("xuatBan");

    public QHoiDap(String variable) {
        this(HoiDap.class, forVariable(variable), INITS);
    }

    public QHoiDap(Path<? extends HoiDap> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHoiDap(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHoiDap(PathMetadata metadata, PathInits inits) {
        this(HoiDap.class, metadata, inits);
    }

    public QHoiDap(Class<? extends HoiDap> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QAsset(type, metadata, inits);
        this.ckEditorPopup = _super.ckEditorPopup;
        this.daiBieuGiamSat = inits.isInitialized("daiBieuGiamSat") ? new vn.toancauxanh.model.QNhanVien(forProperty("daiBieuGiamSat"), inits.get("daiBieuGiamSat")) : null;
        this.daXoa = _super.daXoa;
        this.id = _super.id;
        this.linhVuc = inits.isInitialized("linhVuc") ? new QLinhVucHoiDap(forProperty("linhVuc"), inits.get("linhVuc")) : null;
        this.ngaySua = _super.ngaySua;
        this.ngayTao = _super.ngayTao;
        this.nguoiSua = _super.nguoiSua;
        this.nguoiTao = _super.nguoiTao;
        this.trangThai = _super.trangThai;
        this.trangThaiSoan = _super.trangThaiSoan;
        this.trangThaiTraLoi = _super.trangThaiTraLoi;
    }

}

