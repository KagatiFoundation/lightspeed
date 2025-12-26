package org.kagati.ls.hir.node;

import java.util.List;

public record HirBlock(List<HirNode> statements) implements HirStmt { }