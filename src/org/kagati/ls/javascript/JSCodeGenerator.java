package org.kagati.ls.javascript;

import org.kagati.ls.hir.node.HirAssign;
import org.kagati.ls.hir.node.HirBlock;
import org.kagati.ls.hir.node.HirConst;
import org.kagati.ls.hir.node.HirNode;

public class JSCodeGenerator {
    public String generate(HirBlock block) {
        StringBuilder output = new StringBuilder();
        for (HirNode node: block.statements()) {
            output.append(generateNode(node));
        }
        return output.toString();
    }
    
    private String generateNode(HirNode node) {
        return switch (node) {
            case HirConst c -> String.format("%d", c.value());
            case HirAssign a -> {
                String value = generateNode(a.expr());
                yield String.format("let %s = %s;\n", a.name(), value);
            }
            default -> throw new IllegalStateException("Unknown node: " + node);
        };
    }
}