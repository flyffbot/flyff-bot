import { useCallback } from "react";
import { sendMessage, SendTopic } from "../socket";
import {
  CreateCustomActionSlotDto,
  DeleteCustomActionSlotDto,
  UpdateCastTimeRequestDto,
  UpdateHexKeyCodeRequestDto
} from "./types";

export const useAddCustomActionSlot = () => {
  const addCustomActionSlot = useCallback((request:CreateCustomActionSlotDto) => {
    sendMessage(SendTopic.POST_CUSTOM_ACTION_SLOT, request);
  }, []);

  return addCustomActionSlot;
}

export const useUpdateCustomActionSlotHexValue = () => {
  const updateCustomActionSlotHexValue = useCallback((request:UpdateHexKeyCodeRequestDto) => {
    sendMessage(SendTopic.PUT_CUSTOM_ACTION_SLOT_HEX_KEY_CODE, request);
  }, []);
  return updateCustomActionSlotHexValue;
}

export const useUpdateCustomActionSlotCastTime = () => {
  const updateCustomActionSlotCastTime = useCallback((request:UpdateCastTimeRequestDto) => {
    sendMessage(SendTopic.PUT_COSTOM_ACTION_SLOT_CAST_TIME, request);
  }, []);
  return updateCustomActionSlotCastTime;
}

export const useDeleteCustomActionSlot = () => {
  const deleteCustomActionSlot = useCallback((request:DeleteCustomActionSlotDto) => {
    sendMessage(SendTopic.DELETE_CUSTOM_ACTION_SLOT, request);
  }, []);
  return deleteCustomActionSlot;
}
