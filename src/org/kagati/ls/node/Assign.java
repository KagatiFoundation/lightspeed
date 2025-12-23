package org.kagati.ls.node;

public record Assign(String name, IRNode expr) implements IRNode {
    
}