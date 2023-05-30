import { createSelector, createSlice } from "@reduxjs/toolkit";
import { ActionStatus, Configuration, WindowItem } from "../api/types";
import { RootState } from "../store";
import { deepEqual } from "./utils";

interface ConfigurationState {
  isConnected?: boolean;
  configurations: Configuration[];
  windows: WindowItem[];
}

const initialState: ConfigurationState = {
  configurations: [],
  windows: [],
};

const configurationSlice = createSlice({
  name: "flyffBot",
  initialState,
  reducers: {
    storeConnectionSatus: (
      state,
      args: { type: string; payload: { isConnected: boolean } }
    ) => {
      state.isConnected = args.payload.isConnected;
    },
    storeConfigurations: (
      state,
      args: { type: string; payload: Configuration[] }
    ) => {
      if (!deepEqual(state.configurations, args.payload)) {
        state.configurations = args.payload;
      }
    },
    storeWindowList: (state, args: { type: string; payload: WindowItem[] }) => {
      if (!deepEqual(state.windows, args.payload)) {
        state.windows = args.payload;
      }
    },
  },
});

const rootSelector = (state: RootState) => state.flyffBot;

const isConnectedSelector = createSelector(
  rootSelector,
  (state) => state.isConnected
);

const pipelinesSelector = createSelector(
  rootSelector,
  (state) => state.configurations
);

const windowsSelector = createSelector(rootSelector, (state) => state.windows);

const configurationSelector = (pipelineId: number) =>
  createSelector(rootSelector, (state) =>
    state.configurations.find((item) => item.pipeline.id === pipelineId)
  );

const pipelineConfigurationSelector = (pipelineId: number) =>
  createSelector(
    rootSelector,
    (state) =>
      state.configurations.find((item) => item.pipeline.id === pipelineId)
        ?.pipeline
  );

const hotkeysConfigurationSelector = (pipelineId: number) =>
  createSelector(
    rootSelector,
    (state) =>
      state.configurations.find((item) => item.pipeline.id === pipelineId)
        ?.hotkeys
  );

const customActionSlotsConfigurationSelector = (pipelineId: number) =>
  createSelector(
    rootSelector,
    (state) =>
      state.configurations.find((item) => item.pipeline.id === pipelineId)
        ?.customActionSlots
  );

const isSelectedAndExistsSelector = (pipelineId?: number) =>
  pipelineId
    ? createSelector(
        pipelineConfigurationSelector(pipelineId),
        windowsSelector,
        (pipeline, windows) => {
          const selectedHwnd = pipeline?.selectedWindowHwnd;
          const exists = windows.some((item) => item.hwnd === selectedHwnd);
          return selectedHwnd != null && exists;
        }
      )
    : createSelector(() => false);

const customActionSlotCountSelector = (pipelineId: number) =>
  createSelector(
    customActionSlotsConfigurationSelector(pipelineId),
    (customActionSlots) => customActionSlots?.length ?? 0
  );

const customActionSlotStatusSelector = (pipelineId: number) =>
  createSelector(configurationSelector(pipelineId), (configuration) => {
    const pipeline = configuration?.pipeline;
    const customActionSlots = configuration?.customActionSlots;

    if ((customActionSlots?.length ?? 0) === 0) {
      return ActionStatus.INVISIBLE;
    }

    if (pipeline?.paused) {
      return ActionStatus.PAUSED;
    }

    return pipeline?.customActionSlotRunning
      ? ActionStatus.RUNNING
      : ActionStatus.INVISIBLE;
  });

const hotkeysStatusSelector = (pipelineId: number) =>
  createSelector(configurationSelector(pipelineId), (configuration) => {
    const pipeline = configuration?.pipeline;
    const hotkeys = configuration?.hotkeys;

    if (!hotkeys?.some((item) => item.active)) {
      return ActionStatus.INVISIBLE;
    }

    if (pipeline?.paused) {
      return ActionStatus.PAUSED;
    }

    return hotkeys?.some((key) => key.active)
      ? ActionStatus.RUNNING
      : ActionStatus.INVISIBLE;
  });

export const selectors = {
  rootSelector,
  isConnectedSelector,
  pipelinesSelector,
  windowsSelector,
  configurationSelector,
  pipelineConfigurationSelector,
  hotkeysConfigurationSelector,
  customActionSlotsConfigurationSelector,
  isSelectedAndExistsSelector,
  customActionSlotCountSelector,
  hotkeysStatusSelector,
  customActionSlotStatusSelector,
};

export const FlyffBotActions = configurationSlice.actions;

export const flyffBotReducer = configurationSlice.reducer;
