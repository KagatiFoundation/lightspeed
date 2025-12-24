package org.kagati.ls;

import java.util.List;

import org.kagati.ls.hir.node.HirAdd;
import org.kagati.ls.hir.node.HirAssign;
import org.kagati.ls.hir.node.HirBlock;
import org.kagati.ls.hir.node.HirCompare;
import org.kagati.ls.hir.node.HirConst;
import org.kagati.ls.hir.node.HirIf;
import org.kagati.ls.hir.node.HirInteger;
import org.kagati.ls.hir.node.HirString;
import org.kagati.ls.hir.node.HirVar;
import org.kagati.ls.hir.node.HirCompare.HirCompareType;
import org.kagati.ls.javascript.JSCodeGenerator;

public class Main {
    public static void main(String[] args) {
        HirBlock block = new HirBlock(List.of(
            new HirAssign(
                "x",
                new HirConst(new HirInteger(12))
            ),
            new HirAssign(
                "y",
                new HirConst(new HirString("Ramesh Poudel"))
            ),
            new HirIf(
                new HirCompare(
                    new HirVar("x"),
                    new HirAdd(
                        new HirConst(
                            new HirInteger(450)
                        ), 
                        new HirVar("y")
                    ),
                    HirCompareType.EqEq
                ), 
                new HirBlock(List.of(
                    new HirAssign("a", new HirConst(new HirInteger(1)))
                )), 
                new HirBlock(List.of())
            )
        ));
        JSCodeGenerator cg = new JSCodeGenerator();
        System.out.println(cg.generate(block));
    }    
}