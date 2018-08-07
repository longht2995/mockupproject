package vn.toancauxanh.gg.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLinhVucVanBan is a Querydsl query type for LinhVucVanBan
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLinhVucVanBan extends EntityPathBase<LinhVucVanBan> {

    private static final long serialVersionUID = -2053935944L;

    private static final PathInits INITS = new PathInits("*", "nguoiSua.*.*.*.*", "nguoiTao.*.*.*.*");

    public static final QLinhVucVanBan linhVucVanBan = new QLinhVucVanBan("linhVucVanBan");

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

    public QLinhVucVanBan(String variable) {
        this(LinhVucVanBan.class, forVariable(variable), INITS);
    }

    public QLinhVucVanBan(Path<? extends LinhVucVanBan> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLinhVucVanBan(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLinhVucVanBan(PathMetadata metadata, PathInits inits) {
        this(LinhVucVanBan.class, metadata, inits);
    }

    public QLinhVucVanBan(Class<? extends LinhVucVanBan> type, PathMetadata metadata, PathInits inits) {
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

