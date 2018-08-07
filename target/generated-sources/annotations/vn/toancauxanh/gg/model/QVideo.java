package vn.toancauxanh.gg.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVideo is a Querydsl query type for Video
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QVideo extends EntityPathBase<Video> {

    private static final long serialVersionUID = 1764921818L;

    private static final PathInits INITS = new PathInits("*", "nguoiSua.*.*.*.*", "nguoiTao.*.*.*.*");

    public static final QVideo video = new QVideo("video");

    public final QAsset _super;

    public final StringPath alias = createString("alias");

    public final QChuDeVideo chuDeVideo;

    //inherited
    public final BooleanPath ckEditorPopup;

    //inherited
    public final BooleanPath daXoa;

    public final QFileEntry file;

    //inherited
    public final NumberPath<Long> id;

    public final QImage image;

    public final StringPath moTa = createString("moTa");

    public final DateTimePath<java.util.Date> ngayHetHan = createDateTime("ngayHetHan", java.util.Date.class);

    //inherited
    public final DateTimePath<java.util.Date> ngaySua;

    //inherited
    public final DateTimePath<java.util.Date> ngayTao;

    public final DateTimePath<java.util.Date> ngayXuatBan = createDateTime("ngayXuatBan", java.util.Date.class);

    // inherited
    public final vn.toancauxanh.model.QNhanVien nguoiSua;

    // inherited
    public final vn.toancauxanh.model.QNhanVien nguoiTao;

    public final StringPath tieuDe = createString("tieuDe");

    //inherited
    public final StringPath trangThai;

    //inherited
    public final StringPath trangThaiSoan;

    //inherited
    public final StringPath trangThaiTraLoi;

    public final StringPath txtLoai = createString("txtLoai");

    public final BooleanPath xuatBan = createBoolean("xuatBan");

    public QVideo(String variable) {
        this(Video.class, forVariable(variable), INITS);
    }

    public QVideo(Path<? extends Video> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVideo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVideo(PathMetadata metadata, PathInits inits) {
        this(Video.class, metadata, inits);
    }

    public QVideo(Class<? extends Video> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QAsset(type, metadata, inits);
        this.chuDeVideo = inits.isInitialized("chuDeVideo") ? new QChuDeVideo(forProperty("chuDeVideo"), inits.get("chuDeVideo")) : null;
        this.ckEditorPopup = _super.ckEditorPopup;
        this.daXoa = _super.daXoa;
        this.file = inits.isInitialized("file") ? new QFileEntry(forProperty("file"), inits.get("file")) : null;
        this.id = _super.id;
        this.image = inits.isInitialized("image") ? new QImage(forProperty("image"), inits.get("image")) : null;
        this.ngaySua = _super.ngaySua;
        this.ngayTao = _super.ngayTao;
        this.nguoiSua = _super.nguoiSua;
        this.nguoiTao = _super.nguoiTao;
        this.trangThai = _super.trangThai;
        this.trangThaiSoan = _super.trangThaiSoan;
        this.trangThaiTraLoi = _super.trangThaiTraLoi;
    }

}

