package org.kagati.ls.mir.node;

import java.util.List;

public record MirFunction(List<MirInstruction> instructions) {
    public void dump() {
        for (MirInstruction i: instructions) {
            i.dump(); 
        }
    }
}