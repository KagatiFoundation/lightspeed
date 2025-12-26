package org.kagati.ls.mir.node;

import java.util.ArrayList;
import java.util.List;

import org.kagati.ls.mir.BlockId;
import org.kagati.ls.mir.Temp;

public final class MirBlock {
    private final BlockId id;
    private final List<MirInstruction> instructions = new ArrayList<>();

    public MirBlock(BlockId id) {
        this.id = id;
    }

    public BlockId id() { return id; }

    public void add(MirInstruction instr) {
        instructions.add(instr);
    }

    public List<MirInstruction> instructions() {
        return List.copyOf(instructions);
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("_L" + id + ":\n");
        for (MirInstruction i: instructions) {
            b.append(i.toString());
        }
        return b.toString();
    };
}