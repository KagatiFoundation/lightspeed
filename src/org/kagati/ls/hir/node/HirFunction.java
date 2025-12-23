package org.kagati.ls.hir.node;

import java.util.List;

public non-sealed class HirFunction implements HirStmt {
    final String name;
    final List<HirParam> params;
    final HirBlock body;

    HirFunction(String name, List<HirParam> params, HirBlock body) {
        this.name = name;
        this.params = params;
        this.body = body;
    } 
}