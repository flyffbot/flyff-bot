type UpdateSelectedWindowRequestDto = {
  pipelineId:number;
  hwnd?:string;
}

type CreateHotkeyDto = {
  pipelineId:number;
}

type UpdateHexKeyCodeRequestDto = {
  id:number;
  keyIndex:number;
  hexKeyCode:string;
}

type UpdateDelayRequestDto = {
  id:number;
  delayMs?:number;
}

type UpdateActiveRequestDto = {
  id:number;
  active:boolean;
}

type DeleteHotkeyDto = {
  id:number;
}

type CreateCustomActionSlotDto = {
  pipelineId:number;
}

type UpdateCastTimeRequestDto = {
  id:number;
  castTimeMs?:number;
}

type DeleteCustomActionSlotDto = {
  id:number;
}

export type {
  UpdateSelectedWindowRequestDto,
  CreateHotkeyDto,
  UpdateHexKeyCodeRequestDto,
  UpdateDelayRequestDto,
  UpdateActiveRequestDto,
  DeleteHotkeyDto,
  CreateCustomActionSlotDto,
  UpdateCastTimeRequestDto,
  DeleteCustomActionSlotDto,
}
