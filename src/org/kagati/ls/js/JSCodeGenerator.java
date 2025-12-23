package org.kagati.ls.js;

import org.kagati.ls.node.Assign;
import org.kagati.ls.node.Block;
import org.kagati.ls.node.Const;
import org.kagati.ls.node.IRNode;

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