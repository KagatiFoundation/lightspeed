from typing import List
from ir import Instruction, MoveImmInst, BinaryOpInst, BinaryOpImmInst, ReturnInst, VirtualReg

class MIRRewriter:
    def __init__(self, allocator):
        self.allocator = allocator
        # We reserve TWO distinct scratch registers for independent unspills
        self.scratch1 = "X14"
        self.scratch2 = "X15"

    def rewrite_program(self, instructions: List[Instruction], intervals) -> List[str]:
        final_assembly = []
        
        for inst in instructions:
            # --- STEP 1: RESOLVE UNSPILLS (READS) ---
            # We explicitly look at the structure of the instruction to avoid clobbering
            match inst:
                case BinaryOpInst(op, dest, src1, src2):
                    if intervals[src1.id].spilled:
                        final_assembly.append(f"\tldr {self.scratch1}, {intervals[src1.id].assigned_phys_reg}  ; UNSPILL: Load {src1} to {self.scratch1}")
                    if intervals[src2.id].spilled:
                        final_assembly.append(f"\tldr {self.scratch2}, {intervals[src2.id].assigned_phys_reg}  ; UNSPILL: Load {src2} to {self.scratch2}")
                
                case BinaryOpImmInst(op, dest, src, imm):
                    if intervals[src.id].spilled:
                        final_assembly.append(f"\tldr {self.scratch1}, {intervals[src.id].assigned_phys_reg}  ; UNSPILL: Load {src} to {self.scratch1}")
                
                case ReturnInst(op, src) if src is not None:
                    if intervals[src.id].spilled:
                        final_assembly.append(f"\tldr {self.scratch1}, {intervals[src.id].assigned_phys_reg}  ; UNSPILL: Load {src} to {self.scratch1}")

            # --- STEP 2: TRANSLATE INSTRUCTION ---
            asm_line = self.map_to_assembly(inst, intervals)
            final_assembly.append(asm_line)

            # --- STEP 3: RESOLVE SPILLS (WRITES) ---
            for dest_reg in inst.defs:
                interval = intervals[dest_reg.id]
                if interval.spilled:
                    final_assembly.append(f"\tstr {self.scratch1}, {interval.assigned_phys_reg}  ; SPILL: Save result to stack")
                    
        return final_assembly

    def map_to_assembly(self, inst: Instruction, intervals) -> str:
        
        def get_reg(v_reg: VirtualReg, scratch_fallback: str) -> str:
            interval = intervals[v_reg.id]
            return scratch_fallback if interval.spilled else interval.assigned_phys_reg

        match inst:
            case MoveImmInst(op, dest, imm):
                return f"\t{op.lower()} {get_reg(dest, self.scratch1)}, #{imm}"
                
            case BinaryOpInst(op, dest, src1, src2):
                # src1 maps to scratch1, src2 maps to scratch2, dest maps to scratch1 if spilled
                r_dest = get_reg(dest, self.scratch1)
                r_src1 = get_reg(src1, self.scratch1)
                r_src2 = get_reg(src2, self.scratch2)
                return f"\t{op.lower()} {r_dest}, {r_src1}, {r_src2}"
                
            case BinaryOpImmInst(op, dest, src, imm):
                return f"\t{op.lower()} {get_reg(dest, self.scratch1)}, {get_reg(src, self.scratch1)}, #{imm}"
                
            case ReturnInst(op, src):
                return f"\t{op.lower()} {get_reg(src, self.scratch1)}" if src else f"\t{op.lower()}"