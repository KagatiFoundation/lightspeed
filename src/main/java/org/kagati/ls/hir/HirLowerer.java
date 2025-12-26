package org.kagati.ls.hir;

import java.util.HashMap;
import java.util.Map;

import org.kagati.ls.hir.node.*;
import org.kagati.ls.hir.node.HirCompare.HirCompareType;
import org.kagati.ls.mir.Temp;
import org.kagati.ls.mir.node.*;

import org.kagati.ls.hir.node.HirAdd;

public final class HirLowerer {
    private final Map<String, Temp> env = new HashMap<>();

    public MirFunction lower(HirFunction function, MirBuilder b) {
        for (HirNode node: function.body().statements()) {
            if (node instanceof HirExpr expr) {
                lowerExpression(expr, b);
            }
            else if (node instanceof HirStmt stmt) {
                lowerStatement(stmt, b);
            }
        }
        return b.build();
    }

    private Temp lowerStatement(HirStmt stmt, MirBuilder b) {
        if (stmt instanceof HirAssign assign) {
            return lowerAssignment(assign, b);
        }
        throw new IllegalStateException();
    }

    private Temp lowerAssignment(HirAssign assign, MirBuilder b) {
        Temp temp = b.nextTemp();
        env.put(assign.name(), temp);
        b.emit(new MirAssign(assign.name(), lowerExpression(assign.expr(), b)));
        return temp;
    }

    private Temp lowerExpression(HirExpr expr, MirBuilder b) {
        if (expr instanceof HirAdd add) {
            return lowerAdd(add, b);
        }
        if (expr instanceof HirConst c) {
            return lowerConstant(c, b);
        }
        if (expr instanceof HirCompare c) {
            return lowerCompare(c, b);
        }
        throw new IllegalStateException("Unhandled HIR expr: " + expr);
    }

    private Temp lowerConstant(HirConst c, MirBuilder b) {
        return switch (c.value()) {
            case HirInteger i -> b.createConst(new MirInteger(i.value()));
            case HirString s -> b.createConst(new MirString(s.value()));
        };
    }

    private Temp lowerAdd(HirAdd add, MirBuilder b) {
        Temp lhsTemp = lowerExpression(add.lhs(), b);
        Temp rhsTemp = lowerExpression(add.rhs(), b);
        Temp target = b.createAdd(lhsTemp, rhsTemp);
        return target;
    }

    private Temp lowerCompare(HirCompare compare, MirBuilder b) {
        Temp lhsTemp = lowerExpression(compare.lhs, b);
        Temp rhsTemp = lowerExpression(compare.rhs, b);
        Temp target = b.createCompare(lhsTemp, rhsTemp, lowerCompareType(compare.type));
        return target;
    }

    private MirCompareType lowerCompareType(HirCompareType type) {
        return switch (type) {
            case EqEq -> MirCompareType.Equal;
        };
    }
}