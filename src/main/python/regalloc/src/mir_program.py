from ir import Instruction, LiveInterval
from typing import List, Dict

class MIRProgram:
    def __init__(self):
        self.instructions: List[Instruction] = []
        self.intervals: Dict[int, LiveInterval] = {}

    def add_inst(self, inst: Instruction):
        self.instructions.append(inst)

    def compute_live_intervals(self):
        self.intervals.clear()

        for idx, inst in enumerate(self.instructions):
            for virtual_reg in inst.defs:
                if virtual_reg.id not in self.intervals:
                    self.intervals[virtual_reg.id] = LiveInterval(reg=virtual_reg, start=idx, end=idx)
                else:
                    self.intervals[virtual_reg.id].end = idx

            for virtual_reg in inst.uses:
                if virtual_reg.id not in self.intervals:
                    self.intervals[virtual_reg.id] = LiveInterval(reg=virtual_reg, start=0, end=idx)
                else:
                    self.intervals[virtual_reg.id].end = idx