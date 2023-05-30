import { useCallback } from "react";
import { sendMessage, SendTopic } from "../socket";
import { UpdateSelectedWindowRequestDto } from "./types";

export const useUpdateSelectedWindow = () => {
  const updateSelectedWindow = useCallback(
    (request: UpdateSelectedWindowRequestDto) => {
      sendMessage(SendTopic.PUT_SELECTED_WINDOW, request);
    },
    []
  );

  return { updateSelectedWindow };
};
