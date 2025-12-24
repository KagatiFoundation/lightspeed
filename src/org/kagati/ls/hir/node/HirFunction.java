package org.kagati.ls.hir.node;

import java.util.List;

public non-sealed class HirFunction implements HirStmt {
    private final String name;
    private final List<HirParam> params;
    public HirBlock body;

    public HirFunction(String name, List<HirParam> params, HirBlock body) {
        this.name = name;
        this.params = params;
        this.body = body;
    } 
}