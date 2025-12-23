package org.kagati.ls;

import java.util.List;

import org.kagati.ls.js.JSCodeGenerator;
import org.kagati.ls.node.Assign;
import org.kagati.ls.node.Block;
import org.kagati.ls.node.Const;

public class Main {
    public static void main(String[] args) {
        Block block = new Block(List.of(
            new Assign("x", new Const(12)),
            new Assign("y", new Const(12))
        ));
        JSCodeGenerator cg = new JSCodeGenerator();
        System.out.println(cg.generate(block));
    }    
}