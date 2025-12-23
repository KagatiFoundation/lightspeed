package org.kagati.ls.javascript;

import org.kagati.ls.hir.node.Assign;
import org.kagati.ls.hir.node.Block;
import org.kagati.ls.hir.node.Const;
import org.kagati.ls.hir.node.IRNode;

public class JSCodeGenerator {
    public String generate(Block block) {
        StringBuilder output = new StringBuilder();
        for (IRNode node: block.statements()) {
            output.append(generateNode(node));
        }
        return output.toString();
    }
    
    private String generateNode(IRNode node) {
        return switch (node) {
            case Const c -> String.format("%d", c.value());
            case Assign a -> {
                String value = generateNode(a.expr());
                yield String.format("let %s = %s;\n", a.name(), value);
            }
            default -> throw new IllegalStateException("Unknown node: " + node);
        };
    }
}