from mir_program import MIRProgram
from typing import List, Dict
from dataclasses import dataclass

@dataclass
class Interval:
	register_id: int
	start: int
	end: int


def construct_liveness_list(program: MIRProgram) -> Dict[int, Interval]:
	intervals_map: Dict[int, Interval] = dict()
	currently_live: List[int] = []

	instruction_ptr = len(program.instructions) - 1

	# defs
	while instruction_ptr >= 0:
		instruction = program.instructions[instruction_ptr]

		for virtual_reg in instruction.defs:
			register_id = virtual_reg.id
			if register_id in currently_live:
				intervals_map[register_id].start = instruction_ptr
				currently_live.remove(virtual_reg.id)
			else:
				intervals_map[register_id] = Interval(register_id, start=instruction_ptr, end=instruction_ptr)
		
		# uses
		for virtual_reg in instruction.uses:
			register_id = virtual_reg.id
			if register_id not in currently_live:
				intervals_map[register_id] = Interval(register_id, start=instruction_ptr, end=instruction_ptr)
				currently_live.append(register_id)
			else:
				intervals_map[register_id].start = instruction_ptr

		instruction_ptr -= 1

	return intervals_map