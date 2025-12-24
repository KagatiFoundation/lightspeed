package org.kagati.ls;

import java.util.List;

import org.kagati.ls.hir.HirLowerer;
import org.kagati.ls.hir.node.HirBlock;
import org.kagati.ls.hir.node.HirConst;
import org.kagati.ls.hir.node.HirFunction;
import org.kagati.ls.hir.node.HirInteger;
import org.kagati.ls.mir.node.MirFunction;

public class Main {
    public static void main(String[] args) {
        HirBlock block = new HirBlock(List.of(
            new HirConst(new HirInteger(12)),
            new HirConst(new HirInteger(12)),
            new HirConst(new HirInteger(12)),
            new HirConst(new HirInteger(12)),
            new HirConst(new HirInteger(12)),
            new HirConst(new HirInteger(12)),
            new HirConst(new HirInteger(12)),
            new HirConst(new HirInteger(12)),
            new HirConst(new HirInteger(12)),
            new HirConst(new HirInteger(12)),
            new HirConst(new HirInteger(12))
        ));
        HirFunction function = new HirFunction("add", List.of(), block);
        HirLowerer hirLowerer = new HirLowerer();
        MirFunction mirFunction = hirLowerer.lower(function);
        mirFunction.dump();
    }    
}