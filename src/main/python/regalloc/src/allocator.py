from typing import List, Dict
from ir import LiveInterval, Instruction

class LinearScanAllocator:
    def __init__(self, phys_regs: List[str]):
        self.phys_regs = phys_regs          
        self.free_regs = list(phys_regs)    
        self.active: List[LiveInterval] = [] 
        self.spill_slots = 0                

    def allocate(self, intervals: List[LiveInterval]):
        sorted_intervals = sorted(intervals, key=lambda i: i.start)
        
        for idx, interval in enumerate(sorted_intervals):
            self.expire_old_intervals(interval)
            
            if len(self.free_regs) == 0:
                self.spill_at_interval(interval)
            else:
                reg = self.free_regs.pop(0)
                
                interval.assigned_phys_reg = reg
                self.active.append(interval)
                
                self.active.sort(key=lambda i: i.end)

    def expire_old_intervals(self, current: LiveInterval):
        intervals_to_remove = []
        
        for act in self.active:
            if act.end >= current.start:
                break
            
            self.free_regs.append(act.assigned_phys_reg)
            intervals_to_remove.append(act)
            
        for act in intervals_to_remove:
            self.active.remove(act)

    def spill_at_interval(self, current: LiveInterval):
        spill_candidate = self.active[-1] 
        if spill_candidate.end > current.end:
            current.assigned_phys_reg = spill_candidate.assigned_phys_reg
            spill_candidate.spilled = True
            spill_candidate.assigned_phys_reg = f"[spill_slot_{self.spill_slots}]"
            self.spill_slots += 1
            
            self.active.remove(spill_candidate)
            self.active.append(current)
            self.active.sort(key=lambda i: i.end)
        else:
            current.spilled = True
            current.assigned_phys_reg = f"[spill_slot_{self.spill_slots}]"
            self.spill_slots += 1