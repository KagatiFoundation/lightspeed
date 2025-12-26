package org.kagati.ls.hir.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.kagati.ls.hir.node.*;
import org.kagati.ls.span.SourceSpan;

public class TypeChecker {
    private final List<HirTypeError> errors = new ArrayList<>();

    public void checkNode(HirNode node) {
        switch (node) {
            case HirExpr expr -> checkExpr(expr);
            case HirStmt stmt -> checkStmt(stmt);
        };
    }   

    public void checkStmt(HirStmt stmt) {
        
    }

    public void checkExpr(HirExpr expr) {
        if (expr instanceof HirConst cExpr) {
            checkConstExpr(cExpr);
        }
        else if (expr instanceof HirAdd add) {
            checkAddExpr(add);
        }
    }

    public void checkAddExpr(HirAdd add) {
        checkExpr(add.lhs());
        checkExpr(add.rhs());

        HirType l = add.lhs().type();
        HirType r = add.rhs().type();

        if (!isAddable(l, r)) {
            errors.add(new HirTypeError(
                new SourceSpan(0, 0, 0),
                String.format("Cannot apply '+' to ('%s', '%s')", l, r)
            ));
            add.setType(ErrorType.INSTANCE);
        }
        else {
            add.setType(resultType(l, r));
        }
    }

    private boolean isAddable(HirType lhs, HirType rhs) {
        if (lhs instanceof IntType && rhs instanceof IntType) {
            return true;
        }
        return true;
    }

    private HirType resultType(HirType lhs, HirType rhs) {
        if (lhs instanceof IntType && rhs instanceof IntType) {
            return IntType.INSTANCE;
        }
        return ErrorType.INSTANCE;
    }

    public void checkConstExpr(HirConst constExpr) {

    }

    public List<HirTypeError> errors() {
        return errors;
    }
}