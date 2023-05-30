export type Configuration = {
  pipeline: Pipeline;
  hotkeys: Hotkey[];
  customActionSlots: CustomActionSlotItem[];
};

export type Pipeline = {
  id: number;
  selectedWindowHwnd?: string;
  selectedWindowName?: string;
  paused: boolean;
  customActionSlotRunning: boolean;
};

export type Hotkey = {
  id: number;
  active: boolean;
  delayMs: number;
  hexKeyCode0: AuxCodes;
  hexKeyCode1: KeyCodes;
  pipelineId: number;
  lastTimeExecutedMs: number;
  executing: boolean;
};

export enum AuxCodes {
  EMPTY = "",
  CTRL = "0x11",
  ALT = "0x12",
}

export enum KeyCodes {
  EMPTY = "",
  ONE = "0x31",
  TWO = "0x32",
  THREE = "0x33",
  FOUR = "0x34",
  FIVE = "0x35",
  SIX = "0x36",
  SEVEN = "0x37",
  EIGHT = "0x38",
  NINE = "0x39",
  ZERO = "0x30",
}

export type CustomActionSlotItem = {
  id: number;
  castTimeMs: number;
  hexKeyCode0: string;
  hexKeyCode1: string;
};

export type WindowItem = {
  title: string;
  hwnd: string;
};

export enum ActionStatus {
  INVISIBLE = "INVISIBLE",
  RUNNING = "RUNNING",
  PAUSED = "PAUSED",
}
