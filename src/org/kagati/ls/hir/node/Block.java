package org.kagati.ls.hir.node;

import java.util.List;

public record Block(List<IRNode> statements) implements IRNode { }