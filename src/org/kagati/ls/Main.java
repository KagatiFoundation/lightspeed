package org.kagati.ls;

import java.util.List;

import org.kagati.ls.hir.node.HirAssign;
import org.kagati.ls.hir.node.HirBlock;
import org.kagati.ls.hir.node.HirConst;
import org.kagati.ls.javascript.JSCodeGenerator;

public class Main {
    public static void main(String[] args) {
        HirBlock block = new HirBlock(List.of(
            new HirAssign("x", new HirConst(12)),
            new HirAssign("y", new HirConst(12))
        ));
        JSCodeGenerator cg = new JSCodeGenerator();
        System.out.println(cg.generate(block));
    }    
}