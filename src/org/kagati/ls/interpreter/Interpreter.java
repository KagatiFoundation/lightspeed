package org.kagati.ls.interpreter;

import java.util.HashMap;
import java.util.Map;

import org.kagati.ls.node.Assign;
import org.kagati.ls.node.Block;
import org.kagati.ls.node.Const;
import org.kagati.ls.node.IRNode;
import org.kagati.ls.node.If;
import org.kagati.ls.node.Var;

public class Interpreter {
    private final Map<String, Integer> env = new HashMap<>();

    public void execute(Block block) {
        for (IRNode stmt : block.statements()) {
            eval(stmt);
        }
    }

    private int eval(IRNode node) {
        return switch (node) {
            case Const c -> c.value();
            case Var v -> env.getOrDefault(v.name(), 0);
            case Assign assign -> {
                int value = eval(assign.expr());
                env.put(assign.name(), value);
                yield value;
            }
            case If i -> {
                int cond = eval(i.cond());
                yield cond != 0 ? evalBlock(i.thenBlock()) : evalBlock(i.elseBlock());
            }
            default -> throw new IllegalStateException("Unknown node: " + node);
        };
    }

    private int evalBlock(Block block) {
        int result = 0;
        for (IRNode stmt : block.statements()) {
            result = eval(stmt);
        }
        return result;
    }
}