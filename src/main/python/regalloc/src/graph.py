from ir import LiveInterval
from typing import List, Dict, Set

class InterferenceGraph:
    def __init__(self):
        self.adj_list: Dict[int, Set] = {}

    def build_from_intervals(self, intervals: List[LiveInterval]):
        for interval in intervals:
            self.adj_list[interval.reg] = set()

        for i in range(len(intervals)):
            for j in range(i + 1, len(intervals)):
                a = intervals[i]
                b = intervals[j]

                if a.start < b.end and b.start < a.end:
                    self.adj_list[a.reg].add(b.reg)
                    self.adj_list[b.reg].add(b.reg)
                    self.adj_list[b.reg].add(a.reg)


    def degree(self, reg_id: int) -> int:
        return len(self.adj_list.get(reg_id, set()))