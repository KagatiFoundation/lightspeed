package org.kagati.ls.hir;

import org.kagati.ls.hir.node.HirConst;
import org.kagati.ls.hir.node.HirExpr;
import org.kagati.ls.hir.node.HirFunction;
import org.kagati.ls.hir.node.HirInteger;
import org.kagati.ls.hir.node.HirNode;
import org.kagati.ls.hir.node.HirString;
import org.kagati.ls.mir.node.MirBuilder;
import org.kagati.ls.mir.node.MirConst;
import org.kagati.ls.mir.node.MirFunction;
import org.kagati.ls.mir.node.MirInteger;
import org.kagati.ls.mir.node.MirString;

public final class HirLowerer {
    private final MirBuilder mirBuilder = new MirBuilder();

    public MirFunction lower(HirFunction function) {
        for (HirNode node: function.body.statements()) {
            if (node instanceof HirExpr expr) {
                if (expr instanceof HirConst c) {
                    MirConst mirConst = lower(c);
                    mirBuilder.emit(mirConst);
                }
            }
        }
        return mirBuilder.build();
    }

    private MirConst lower(HirConst value) {
        return switch (value.value()) {
            case HirInteger i -> new MirConst(mirBuilder.nextTemp(), new MirInteger(i.value()));
            case HirString s -> new MirConst(mirBuilder.nextTemp(), new MirString(s.value()));
            default -> throw new IllegalStateException();
        };
    }
}