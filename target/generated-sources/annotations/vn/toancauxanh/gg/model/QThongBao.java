package vn.toancauxanh.gg.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QThongBao is a Querydsl query type for ThongBao
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QThongBao extends EntityPathBase<ThongBao> {

    private static final long serialVersionUID = 41145917L;

    private static final PathInits INITS = new PathInits("*", "nguoiSua.*.*.*.*", "nguoiTao.*.*.*.*");

    public static final QThongBao thongBao = new QThongBao("thongBao");

    public final QAsset _super;

    //inherited
    public final BooleanPath ckEditorPopup;

    public final QCoQuanBanHanh coQuanBanHanh;

    //inherited
    public final BooleanPath daXoa;

    public final QFileEntry file;

    //inherited
    public final NumberPath<Long> id;

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

    public final StringPath tieuDe = createString("tieuDe");

    //inherited
    public final StringPath trangThai;

    //inherited
    public final StringPath trangThaiSoan;

    //inherited
    public final StringPath trangThaiTraLoi;

    public final BooleanPath xuatBan = createBoolean("xuatBan");

    public QThongBao(String variable) {
        this(ThongBao.class, forVariable(variable), INITS);
    }

    public QThongBao(Path<? extends ThongBao> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QThongBao(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QThongBao(PathMetadata metadata, PathInits inits) {
        this(ThongBao.class, metadata, inits);
    }

    public QThongBao(Class<? extends ThongBao> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QAsset(type, metadata, inits);
        this.ckEditorPopup = _super.ckEditorPopup;
        this.coQuanBanHanh = inits.isInitialized("coQuanBanHanh") ? new QCoQuanBanHanh(forProperty("coQuanBanHanh"), inits.get("coQuanBanHanh")) : null;
        this.daXoa = _super.daXoa;
        this.file = inits.isInitialized("file") ? new QFileEntry(forProperty("file"), inits.get("file")) : null;
        this.id = _super.id;
        this.ngaySua = _super.ngaySua;
        this.ngayTao = _super.ngayTao;
        this.nguoiSua = _super.nguoiSua;
        this.nguoiTao = _super.nguoiTao;
        this.trangThai = _super.trangThai;
        this.trangThaiSoan = _super.trangThaiSoan;
        this.trangThaiTraLoi = _super.trangThaiTraLoi;
    }

}

