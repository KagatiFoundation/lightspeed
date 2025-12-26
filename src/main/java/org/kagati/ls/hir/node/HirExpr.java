package org.kagati.ls.hir.node;

import org.kagati.ls.hir.type.HirType;

public non-sealed abstract class HirExpr implements HirNode {
    private HirType type;

    public HirType type() { return type; }

    public void setType(HirType type) { this.type = type; }
}