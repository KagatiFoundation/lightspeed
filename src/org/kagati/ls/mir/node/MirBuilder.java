package org.kagati.ls.mir.node;

import java.util.ArrayList;
import java.util.List;

import org.kagati.ls.mir.BlockId;
import org.kagati.ls.mir.Temp;

public final class MirBuilder {
    private int nextTemp = 0;
    private int nextBlock = 0;

    private final List<MirBlock> blocks = new ArrayList<>();
    private MirBlock currentBlock;

    public MirBuilder() {
        currentBlock = new MirBlock(nextBlock());
        blocks.add(currentBlock);
    }

    public Temp nextTemp() { return new Temp(nextTemp++); }

    public BlockId nextBlock() { return new BlockId(nextBlock++); }

    public void emit(MirInstruction i) {
        currentBlock.add(i);
    }

    public void startBlock(MirBlock block) {
        currentBlock = block;
        blocks.add(block);
    }

    public MirFunction build() {
        return new MirFunction(List.copyOf(blocks));
    }
}