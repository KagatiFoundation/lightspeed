package org.kagati.ls.hir.node;

import java.util.List;

public record HirFunction(String name, List<String> params, HirBlock body) implements HirStmt { }