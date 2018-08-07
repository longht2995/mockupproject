package vn.toancauxanh.gg.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVanBan is a Querydsl query type for VanBan
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QVanBan extends EntityPathBase<VanBan> {

    private static final long serialVersionUID = -1129122707L;

    private static final PathInits INITS = new PathInits("*", "nguoiSua.*.*.*.*", "nguoiTao.*.*.*.*");

    public static final QVanBan vanBan = new QVanBan("vanBan");

    public final QAsset _super;

    public final QCapBanHanh capBanHanh;

    //inherited
    public final BooleanPath ckEditorPopup;

    public final QCoQuanBanHanh coQuanBanHanh;

    //inherited
    public final BooleanPath daXoa;

    public final QFileEntry file;

    //inherited
    public final NumberPath<Long> id;

    public final QLinhVucVanBan linhVucVanBan;

    public final QLoaiVanBan loaiVanBan;

    public final StringPath moTa = createString("moTa");

    public final DateTimePath<java.util.Date> ngayBanHanh = createDateTime("ngayBanHanh", java.util.Date.class);

    public final DateTimePath<java.util.Date> ngayHieuLuc = createDateTime("ngayHieuLuc", java.util.Date.class);

    //inherited
    public final DateTimePath<java.util.Date> ngaySua;

    //inherited
    public final DateTimePath<java.util.Date> ngayTao;

    public final StringPath nguoiKy = createString("nguoiKy");

    // inherited
    public final vn.toancauxanh.model.QNhanVien nguoiSua;

    // inherited
    public final vn.toancauxanh.model.QNhanVien nguoiTao;

    public final StringPath soKyHieu = createString("soKyHieu");

    //inherited
    public final StringPath trangThai;

    //inherited
    public final StringPath trangThaiSoan;

    //inherited
    public final StringPath trangThaiTraLoi;

    public final StringPath trichYeu = createString("trichYeu");

    public final BooleanPath xuatBan = createBoolean("xuatBan");

    public QVanBan(String variable) {
        this(VanBan.class, forVariable(variable), INITS);
    }

    public QVanBan(Path<? extends VanBan> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVanBan(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVanBan(PathMetadata metadata, PathInits inits) {
        this(VanBan.class, metadata, inits);
    }

    public QVanBan(Class<? extends VanBan> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QAsset(type, metadata, inits);
        this.capBanHanh = inits.isInitialized("capBanHanh") ? new QCapBanHanh(forProperty("capBanHanh"), inits.get("capBanHanh")) : null;
        this.ckEditorPopup = _super.ckEditorPopup;
        this.coQuanBanHanh = inits.isInitialized("coQuanBanHanh") ? new QCoQuanBanHanh(forProperty("coQuanBanHanh"), inits.get("coQuanBanHanh")) : null;
        this.daXoa = _super.daXoa;
        this.file = inits.isInitialized("file") ? new QFileEntry(forProperty("file"), inits.get("file")) : null;
        this.id = _super.id;
        this.linhVucVanBan = inits.isInitialized("linhVucVanBan") ? new QLinhVucVanBan(forProperty("linhVucVanBan"), inits.get("linhVucVanBan")) : null;
        this.loaiVanBan = inits.isInitialized("loaiVanBan") ? new QLoaiVanBan(forProperty("loaiVanBan"), inits.get("loaiVanBan")) : null;
        this.ngaySua = _super.ngaySua;
        this.ngayTao = _super.ngayTao;
        this.nguoiSua = _super.nguoiSua;
        this.nguoiTao = _super.nguoiTao;
        this.trangThai = _super.trangThai;
        this.trangThaiSoan = _super.trangThaiSoan;
        this.trangThaiTraLoi = _super.trangThaiTraLoi;
    }

}

