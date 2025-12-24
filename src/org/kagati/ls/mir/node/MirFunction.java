package org.kagati.ls.mir.node;

import java.util.List;

public record MirFunction(List<MirInstruction> instructions) {
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (MirInstruction i: instructions) {
            b.append(i.toString());
        }
        return b.toString();
    }
}