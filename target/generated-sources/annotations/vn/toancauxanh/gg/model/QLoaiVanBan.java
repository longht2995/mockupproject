package vn.toancauxanh.gg.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLoaiVanBan is a Querydsl query type for LoaiVanBan
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLoaiVanBan extends EntityPathBase<LoaiVanBan> {

    private static final long serialVersionUID = -833429320L;

    private static final PathInits INITS = new PathInits("*", "nguoiSua.*.*.*.*", "nguoiTao.*.*.*.*");

    public static final QLoaiVanBan loaiVanBan = new QLoaiVanBan("loaiVanBan");

    public final vn.toancauxanh.model.QModel _super;

    public final BooleanPath checkApDung = createBoolean("checkApDung");

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

    public final StringPath tenLoai = createString("tenLoai");

    //inherited
    public final StringPath trangThai;

    public QLoaiVanBan(String variable) {
        this(LoaiVanBan.class, forVariable(variable), INITS);
    }

    public QLoaiVanBan(Path<? extends LoaiVanBan> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLoaiVanBan(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLoaiVanBan(PathMetadata metadata, PathInits inits) {
        this(LoaiVanBan.class, metadata, inits);
    }

    public QLoaiVanBan(Class<? extends LoaiVanBan> type, PathMetadata metadata, PathInits inits) {
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

