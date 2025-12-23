package org.kagati.ls.hir.node;

public record Assign(String name, IRNode expr) implements IRNode {
    
}