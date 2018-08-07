package vn.toancauxanh.gg.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDanhMuc is a Querydsl query type for DanhMuc
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDanhMuc extends EntityPathBase<DanhMuc> {

    private static final long serialVersionUID = 562850531L;

    private static final PathInits INITS = new PathInits("*", "nguoiSua.*.*.*.*", "nguoiTao.*.*.*.*");

    public static final QDanhMuc danhMuc = new QDanhMuc("danhMuc");

    public final vn.toancauxanh.model.QModel _super;

    public final StringPath alias = createString("alias");

    //inherited
    public final BooleanPath daXoa;

    public final StringPath description = createString("description");

    //inherited
    public final NumberPath<Long> id;

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.util.Date> ngaySua;

    //inherited
    public final DateTimePath<java.util.Date> ngayTao;

    // inherited
    public final vn.toancauxanh.model.QNhanVien nguoiSua;

    // inherited
    public final vn.toancauxanh.model.QNhanVien nguoiTao;

    public final QDanhMuc parent;

    public final NumberPath<Integer> soThuTu = createNumber("soThuTu", Integer.class);

    //inherited
    public final StringPath trangThai;

    public QDanhMuc(String variable) {
        this(DanhMuc.class, forVariable(variable), INITS);
    }

    public QDanhMuc(Path<? extends DanhMuc> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDanhMuc(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDanhMuc(PathMetadata metadata, PathInits inits) {
        this(DanhMuc.class, metadata, inits);
    }

    public QDanhMuc(Class<? extends DanhMuc> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new vn.toancauxanh.model.QModel(type, metadata, inits);
        this.daXoa = _super.daXoa;
        this.id = _super.id;
        this.ngaySua = _super.ngaySua;
        this.ngayTao = _super.ngayTao;
        this.nguoiSua = _super.nguoiSua;
        this.nguoiTao = _super.nguoiTao;
        this.parent = inits.isInitialized("parent") ? new QDanhMuc(forProperty("parent"), inits.get("parent")) : null;
        this.trangThai = _super.trangThai;
    }

}

