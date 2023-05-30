import SockJS from "sockjs-client";
import * as Stomp from "stompjs";
import { mockedConfigurations } from "../mocks/configurations";
import { mockedWindowList } from "../mocks/windows";
import { isSocketMockEnabled, isStompDebugLogEnabled } from "./utils";

const stompClient = isSocketMockEnabled()
  ? ({} as Stomp.Client)
  : Stomp.over(new SockJS("/flyff-bot-socket"));

if (!isStompDebugLogEnabled()) {
  stompClient.debug = () => {};
}

export enum SendTopic {
  POST_CUSTOM_ACTION_SLOT = "/post-custom-action-slot",
  PUT_CUSTOM_ACTION_SLOT_HEX_KEY_CODE = "/put-custom-action-slot-hex-key-code",
  PUT_COSTOM_ACTION_SLOT_CAST_TIME = "/put-custom-action-slot-cast-time",
  DELETE_CUSTOM_ACTION_SLOT = "/delete-custom-action-slot",

  POST_HOTKEY = "/post-hotkey",
  PUT_HOTKEY_HEX_KEY_CODE = "/put-hotkey-hex-key-code",
  PUT_HOTKEY_DELAY = "/put-hotkey-delay",
  PUT_HOTKEY_ACTIVE = "/put-hotkey-active",
  DELETE_HOTKEY = "/delete-hotkey",

  GET_CONFIGURATIONS = "/get-configuration",
  GET_WINDOW_LIST = "/get-configuration",
  PUT_SELECTED_WINDOW = "/put-selected-window",
}

export enum ReceiveTopic {
  UPDATED_CONFIGURATIONS_TOPIC = "/updated-configuration",
  UPDATED_WINDOW_LIST = "/updated-window-list",
}

const connect = () =>
  new Promise<boolean>((resolve) => {
    if (isSocketMockEnabled()) {
      resolve(true);
      return;
    }
    stompClient.connect({}, (_frame) => resolve(true));
  });

function sendMessage<T>(topic: SendTopic, payload?: T) {
  if (isSocketMockEnabled()) {
    return;
  }

  if (!stompClient) {
    console.error("Stomp client not ready, request not sent!");
    return;
  }

  stompClient.send(
    `/app${topic}`,
    {},
    payload ? JSON.stringify(payload) : undefined
  );
}

function subscribe<T>(topic: ReceiveTopic, callback: (response: T) => void) {
  if (!isSocketMockEnabled()) {
    stompClient.subscribe(`/topic${topic}`, (socketResponse) => {
      callback(JSON.parse(socketResponse.body));
    });
    return;
  }

  switch (topic) {
    case ReceiveTopic.UPDATED_CONFIGURATIONS_TOPIC:
      return callback(mockedConfigurations as T);
    case ReceiveTopic.UPDATED_WINDOW_LIST:
      return callback(mockedWindowList as T);
  }
}

export { connect, sendMessage, subscribe };
