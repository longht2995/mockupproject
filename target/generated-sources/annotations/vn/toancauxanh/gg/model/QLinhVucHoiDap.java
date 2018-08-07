package vn.toancauxanh.gg.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLinhVucHoiDap is a Querydsl query type for LinhVucHoiDap
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLinhVucHoiDap extends EntityPathBase<LinhVucHoiDap> {

    private static final long serialVersionUID = 1853005501L;

    private static final PathInits INITS = new PathInits("*", "nguoiSua.*.*.*.*", "nguoiTao.*.*.*.*");

    public static final QLinhVucHoiDap linhVucHoiDap = new QLinhVucHoiDap("linhVucHoiDap");

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

    public final StringPath tenLinhVuc = createString("tenLinhVuc");

    //inherited
    public final StringPath trangThai;

    public QLinhVucHoiDap(String variable) {
        this(LinhVucHoiDap.class, forVariable(variable), INITS);
    }

    public QLinhVucHoiDap(Path<? extends LinhVucHoiDap> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLinhVucHoiDap(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLinhVucHoiDap(PathMetadata metadata, PathInits inits) {
        this(LinhVucHoiDap.class, metadata, inits);
    }

    public QLinhVucHoiDap(Class<? extends LinhVucHoiDap> type, PathMetadata metadata, PathInits inits) {
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

