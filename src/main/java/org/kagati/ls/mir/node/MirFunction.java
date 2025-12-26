package org.kagati.ls.mir.node;

import java.util.List;

public final record MirFunction(List<MirBlock> blocks) {
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (MirBlock block: blocks) {
            b.append(block.toString());
        }
        return b.toString();
    };
}