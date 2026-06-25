from dataclasses import dataclass, field
from typing import List, Optional

@dataclass
class VirtualReg:
    id: int
    
    def __repr__(self):
        return f"v{self.id}"


@dataclass
class LiveInterval:
    reg: VirtualReg
    start: int = -1
    end: int = -1
    spilled: bool = False
    assigned_phys_reg: Optional[str] = None

    def __repr__(self):
        phys = f" -> {self.assigned_phys_reg}" if self.assigned_phys_reg else ""
        spill = " [SPILLED]" if self.spilled else ""
        return f"Interval({self.reg}{phys}: [{self.start}, {self.end}]{spill})"


@dataclass
class Instruction:
    op: str

    @property
    def defs(self) -> List[VirtualReg]:
        """Subclasses override this to specify which registers they write to."""
        return []

    @property
    def uses(self) -> List[VirtualReg]:
        """Subclasses override this to specify which registers they read from."""
        return []


@dataclass
class MoveImmInst(Instruction):
    """Loads a constant/immediate into a destination register. e.g., v0 = MOV #10"""
    dest: VirtualReg
    imm: int

    @property
    def defs(self) -> List[VirtualReg]:
        return [self.dest]

    def __repr__(self):
        return f"{self.dest} = {self.op} #{self.imm}"


@dataclass
class BinaryOpInst(Instruction):
    """Performs operations involving two sources and one destination. e.g., v2 = ADD v0, v1"""
    dest: VirtualReg
    src1: VirtualReg
    src2: VirtualReg

    @property
    def defs(self) -> List[VirtualReg]:
        return [self.dest]

    @property
    def uses(self) -> List[VirtualReg]:
        return [self.src1, self.src2]

    def __repr__(self):
        return f"{self.dest} = {self.op} {self.src1}, {self.src2}"


@dataclass
class BinaryOpImmInst(Instruction):
    """Performs operations with a source register and an immediate. e.g., v3 = ADD v2, #5"""
    dest: VirtualReg
    src: VirtualReg
    imm: int

    @property
    def defs(self) -> List[VirtualReg]:
        return [self.dest]

    @property
    def uses(self) -> List[VirtualReg]:
        return [self.src]

    def __repr__(self):
        return f"{self.dest} = {self.op} {self.src}, #{self.imm}"


@dataclass
class ReturnInst(Instruction):
    """Returns a value from a function. e.g., RET v3"""
    src: Optional[VirtualReg] = None

    @property
    def uses(self) -> List[VirtualReg]:
        return [self.src] if self.src else []

    def __repr__(self):
        return f"{self.op} {self.src}" if self.src else self.op