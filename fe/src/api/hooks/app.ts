import { useCallback, useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { FlyffBotActions, selectors } from "../slice";

import {
  connect,
  sendMessage,
  subscribe,
  SendTopic,
  ReceiveTopic,
} from "../socket";
import { clearNullValues } from "../utils";
import { Configuration, WindowItem } from "../types";

const useSocketConnect = () => {
  const [isConnected, setIsConnected] = useState(false);

  useEffect(() => {
    if (isConnected) {
      return;
    }
    connect().then(setIsConnected);
  }, [isConnected]);

  return { isConnected };
};

const useRequestConfiguration = () => {
  const requestConfiguration = useCallback(
    () => sendMessage(SendTopic.GET_CONFIGURATIONS),
    []
  );

  return { requestConfiguration };
};

const useSubribeConfigurationUpdates = () => {
  const isConnected = useSelector(selectors.isConnectedSelector);
  const [configurations, setConfigurations] = useState<Configuration[]>([]);

  useEffect(() => {
    if (!isConnected) {
      return;
    }
    subscribe(
      ReceiveTopic.UPDATED_CONFIGURATIONS_TOPIC,
      (response: Configuration[]) =>
        setConfigurations(clearNullValues(response))
    );
  }, [isConnected]);

  return { configurations };
};

const useSubribeWindowListUpdates = () => {
  const isConnected = useSelector(selectors.isConnectedSelector);
  const [windowList, setWindowList] = useState<WindowItem[]>([]);

  useEffect(() => {
    if (!isConnected) {
      return;
    }
    subscribe(ReceiveTopic.UPDATED_WINDOW_LIST, (response: WindowItem[]) =>
      setWindowList(clearNullValues(response))
    );
  }, [isConnected]);

  return { windowList };
};

export const useInitSocket = () => {
  const dispatch = useDispatch();
  const { isConnected } = useSocketConnect();
  const { configurations } = useSubribeConfigurationUpdates();
  const { windowList } = useSubribeWindowListUpdates();
  const { requestConfiguration } = useRequestConfiguration();

  useEffect(() => {
    if (isConnected) {
      requestConfiguration();
    }
  }, [isConnected, requestConfiguration]);

  useEffect(() => {
    // Store to redux to share value to other components
    dispatch(FlyffBotActions.storeConnectionSatus({ isConnected }));
  }, [dispatch, isConnected]);

  useEffect(() => {
    // Store to redux to share value to other components
    dispatch(FlyffBotActions.storeConfigurations(configurations));
  }, [dispatch, configurations]);

  useEffect(() => {
    // Store to redux to share value to other components
    dispatch(FlyffBotActions.storeWindowList(windowList));
  }, [dispatch, windowList]);
};
