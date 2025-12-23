package org.kagati.ls.node;

import java.util.List;

public record Block(List<IRNode> statements) implements IRNode { }