import { AuxCodes, Configuration, KeyCodes } from "../api/types";

export const mockedConfigurations: Configuration[] = [
  {
    pipeline: {
      id: 9991,
      selectedWindowHwnd: undefined,
      selectedWindowName: undefined,
      paused: true,
      customActionSlotRunning: false,
    },
    hotkeys: [
      {
        id: 3,
        hexKeyCode0: AuxCodes.ALT,
        hexKeyCode1: KeyCodes.ONE,
        delayMs: 500,
        active: true,
        lastTimeExecutedMs: 0,
        pipelineId: 1,
        executing: false,
      },
      {
        id: 9,
        hexKeyCode0: AuxCodes.EMPTY,
        hexKeyCode1: KeyCodes.EMPTY,
        delayMs: 500,
        active: false,
        lastTimeExecutedMs: 0,
        pipelineId: 1,
        executing: false,
      },
      {
        id: 10,
        hexKeyCode0: AuxCodes.EMPTY,
        hexKeyCode1: KeyCodes.EMPTY,
        delayMs: 500,
        active: false,
        lastTimeExecutedMs: 0,
        pipelineId: 1,
        executing: false,
      },
    ],
    customActionSlots: [],
  },
  {
    pipeline: {
      id: 9992,
      selectedWindowHwnd: undefined,
      selectedWindowName: undefined,
      paused: true,
      customActionSlotRunning: false,
    },
    hotkeys: [],
    customActionSlots: [],
  },
];
