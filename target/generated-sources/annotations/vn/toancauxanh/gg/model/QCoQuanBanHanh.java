package vn.toancauxanh.gg.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCoQuanBanHanh is a Querydsl query type for CoQuanBanHanh
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCoQuanBanHanh extends EntityPathBase<CoQuanBanHanh> {

    private static final long serialVersionUID = -1535432476L;

    private static final PathInits INITS = new PathInits("*", "nguoiSua.*.*.*.*", "nguoiTao.*.*.*.*");

    public static final QCoQuanBanHanh coQuanBanHanh = new QCoQuanBanHanh("coQuanBanHanh");

    public final vn.toancauxanh.model.QModel _super;

    //inherited
    public final BooleanPath daXoa;

    //inherited
    public final NumberPath<Long> id;

    public final StringPath moTa = createString("moTa");

    //inherited
    public final DateTimePath<java.util.Date> ngaySua;

    //inherited
    public final DateTimePath<java.util.Date> ngayTao;

    // inherited
    public final vn.toancauxanh.model.QNhanVien nguoiSua;

    // inherited
    public final vn.toancauxanh.model.QNhanVien nguoiTao;

    public final StringPath tenCoQuan = createString("tenCoQuan");

    //inherited
    public final StringPath trangThai;

    public QCoQuanBanHanh(String variable) {
        this(CoQuanBanHanh.class, forVariable(variable), INITS);
    }

    public QCoQuanBanHanh(Path<? extends CoQuanBanHanh> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCoQuanBanHanh(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCoQuanBanHanh(PathMetadata metadata, PathInits inits) {
        this(CoQuanBanHanh.class, metadata, inits);
    }

    public QCoQuanBanHanh(Class<? extends CoQuanBanHanh> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new vn.toancauxanh.model.QModel(type, metadata, inits);
        this.daXoa = _super.daXoa;
        this.id = _super.id;
        this.ngaySua = _super.ngaySua;
        this.ngayTao = _super.ngayTao;
        this.nguoiSua = _super.nguoiSua;
        this.nguoiTao = _super.nguoiTao;
        this.trangThai = _super.trangThai;
    }

}

