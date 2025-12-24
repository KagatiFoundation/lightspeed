package org.kagati.ls.javascript;

import org.kagati.ls.hir.node.HirAdd;
import org.kagati.ls.hir.node.HirAssign;
import org.kagati.ls.hir.node.HirBlock;
import org.kagati.ls.hir.node.HirCompare;
import org.kagati.ls.hir.node.HirConst;
import org.kagati.ls.hir.node.HirExpr;
import org.kagati.ls.hir.node.HirIf;
import org.kagati.ls.hir.node.HirInteger;
import org.kagati.ls.hir.node.HirString;
import org.kagati.ls.hir.node.HirVar;
import org.kagati.ls.hir.node.HirCompare.HirCompareType;
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
            // Expressions
            case HirConst c -> generateExpression(c);
            case HirCompare c -> generateExpression(c);
            case HirAdd a -> generateExpression(a);
            case HirVar v -> generateExpression(v);
            // Expressions end

            case HirBlock b -> generate(b);
            case HirAssign a -> {
                String value = generateNode(a.expr());
                yield String.format("let %s = %s;\n", a.name(), value);
            }
            case HirIf branch -> generateIfNode(branch);
            default -> throw new IllegalStateException("Unknown node: " + node);
        };
    }

    private String generateExpression(HirExpr expr) {
        return switch (expr) {
            case HirCompare c -> {
                String lhs = generateNode(c.lhs);
                String rhs = generateNode(c.rhs);
                yield switch (c.type) {
                    case HirCompareType.EqEq: yield String.format("(%s === %s)", lhs, rhs);
                };
            }
            case HirConst c -> {
                yield switch (c.value()) {
                    case HirInteger i -> String.format("%d", i.value());
                    case HirString s -> String.format("\"%s\"", s.value());
                    default -> throw new IllegalStateException("Unknown value: " + c.value());
                };
            }
            case HirAdd a -> {
                String lhs = generateExpression(a.lhs());
                String rhs = generateExpression(a.rhs());
                yield String.format("(%s + %s)", lhs, rhs);
            }
            case HirVar v -> v.name();
            default -> throw new IllegalStateException("Unknown node: " + expr);
        };
    }

    private String generateIfNode(HirIf hirIf) {
        StringBuilder builder = new StringBuilder();
        builder.append("if (")
            .append(generateNode(hirIf.cond()))
            .append(") {\n")
            .append(generate(hirIf.thenBlock()))
            .append("}\nelse {\n")
            .append(generate(hirIf.elseBlock()))
            .append("}\n");
        return builder.toString();
    }
}