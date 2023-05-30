import { useCallback } from "react";
import { sendMessage, SendTopic } from "../socket";
import {
  CreateHotkeyDto,
  DeleteHotkeyDto,
  UpdateActiveRequestDto,
  UpdateDelayRequestDto,
  UpdateHexKeyCodeRequestDto
} from "./types";

export const useAddHotkey = () => {
  const addHotkey = useCallback((request:CreateHotkeyDto) => {
    sendMessage(SendTopic.POST_HOTKEY, request);
  }, []);

  return addHotkey;
}

export const useUpdateHotkeyHexValue = () => {
  const updateHotkeyHexValue = useCallback((request:UpdateHexKeyCodeRequestDto) => {
    sendMessage(SendTopic.PUT_HOTKEY_HEX_KEY_CODE, request);
  }, []);
  return updateHotkeyHexValue;
}

export const useUpdateHotkeyDelay = () => {
  const updateHotkeyDelay = useCallback((request:UpdateDelayRequestDto) => {
    sendMessage(SendTopic.PUT_HOTKEY_DELAY, request);
  }, []);
  return updateHotkeyDelay;
}

export const useUpdateHotkeyActive = () => {
  const updateHotkeyActive = useCallback((request:UpdateActiveRequestDto) => {
    sendMessage(SendTopic.PUT_HOTKEY_ACTIVE, request);
  }, []);
  return updateHotkeyActive;
}

export const useDeleteHotkey = () => {
  const deleteHotkey = useCallback((request:DeleteHotkeyDto) => {
    sendMessage(SendTopic.DELETE_HOTKEY, request);
  }, []);
  return deleteHotkey;
}
