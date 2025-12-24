package org.kagati.ls.hir;

import org.kagati.ls.hir.node.HirAdd;
import org.kagati.ls.hir.node.HirConst;
import org.kagati.ls.hir.node.HirExpr;
import org.kagati.ls.hir.node.HirFunction;
import org.kagati.ls.hir.node.HirInteger;
import org.kagati.ls.hir.node.HirNode;
import org.kagati.ls.hir.node.HirString;
import org.kagati.ls.mir.node.MirAdd;
import org.kagati.ls.mir.node.MirBuilder;
import org.kagati.ls.mir.node.MirConst;
import org.kagati.ls.mir.node.MirFunction;
import org.kagati.ls.mir.node.MirInteger;
import org.kagati.ls.mir.node.MirString;
import org.kagati.ls.mir.node.Temp;

public final class HirLowerer {
    public MirFunction lower(HirFunction function, MirBuilder b) {
        for (HirNode node: function.body().statements()) {
            if (node instanceof HirExpr expr) {
                lowerExpression(expr, b);
            }
        }
        return b.build();
    }

    private Temp lowerExpression(HirExpr expr, MirBuilder b) {
        if (expr instanceof HirAdd add) {
            return lowerAdd(add, b);
        }
        if (expr instanceof HirConst c) {
            return lowerConstant(c, b);
        }
        throw new IllegalStateException("Unhandled HIR expr: " + expr);
    }

    private Temp lowerConstant(HirConst c, MirBuilder b) {
        Temp temp = b.freshTemp();
        switch (c.value()) {
            case HirInteger i -> b.emit(new MirConst(temp, new MirInteger(i.value())));
            case HirString s -> b.emit(new MirConst(temp, new MirString(s.value())));
        };
        return temp;
    }

    private Temp lowerAdd(HirAdd add, MirBuilder b) {
        Temp lhsTemp = lowerExpression(add.lhs(), b);
        Temp rhsTemp = lowerExpression(add.rhs(), b);
        Temp target = b.freshTemp();
        b.emit(new MirAdd(target, lhsTemp, rhsTemp));
        return target;
    }
}