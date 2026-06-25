from ir import VirtualReg, MoveImmInst, BinaryOpInst, BinaryOpImmInst
from mir_program import MIRProgram

from liveness_analyzer import construct_liveness_list
import pprint

def main():
    v0 = VirtualReg(0)
    v1 = VirtualReg(1)
    v2 = VirtualReg(2)
    v3 = VirtualReg(3)
    v4 = VirtualReg(4)
    v5 = VirtualReg(5)
    v6 = VirtualReg(6)
    v7 = VirtualReg(7)
    v8 = VirtualReg(8)
    v9 = VirtualReg(9)

    prog = MIRProgram()
    
    prog.add_inst(MoveImmInst(op="MOV", dest=v0, imm=10))
    prog.add_inst(MoveImmInst(op="MOV", dest=v1, imm=20))
    prog.add_inst(BinaryOpInst(op="ADD", dest=v2, src1=v0, src2=v1))
    prog.add_inst(MoveImmInst(op="MOV", dest=v3, imm=30))
    prog.add_inst(BinaryOpImmInst(op="ADD", dest=v4, src=v3, imm=2))
    prog.add_inst(BinaryOpImmInst(op="ADD", dest=v5, src=v4, imm=5))
    prog.add_inst(BinaryOpImmInst(op="SUB", dest=v6, src=v1, imm=1))

    liveness = construct_liveness_list(prog)

    pprint.pprint(liveness)

if __name__ == "__main__":
    main()