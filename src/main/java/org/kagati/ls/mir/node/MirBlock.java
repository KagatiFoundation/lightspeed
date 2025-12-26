package org.kagati.ls.mir.node;

import java.util.ArrayList;
import java.util.List;

import org.kagati.ls.mir.BlockId;

public final class MirBlock {
    private final BlockId id;
    private final List<MirInstruction> instrs = new ArrayList<>();

    public MirBlock(BlockId id) {
        this.id = id;
    }

    public BlockId id() { return id; }

    public void add(MirInstruction instr) {
        instrs.add(instr);
    }

    public List<MirInstruction> instructions() {
        return List.copyOf(instrs);
    }
}