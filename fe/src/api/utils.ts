const deepEqual = (obj1: any, obj2: any) => {
  return JSON.stringify(obj1) === JSON.stringify(obj2);
};

const clearNullValues = <T>(obj: T) => {
  Object.entries(obj as any).forEach(([key, value]) => {
    if (!!value && typeof value === "object") {
      clearNullValues(value);
    } else if (value === null) {
      (obj as any)[key] = undefined;
    }
  });
  return obj;
};

const parseEnvBoolean = (raw?: string) =>
  (raw ?? "false").toLocaleLowerCase() === "true";

const isSocketMockEnabled = () =>
  parseEnvBoolean(process.env.REACT_APP_SOCKET_MOCK);

const isStompDebugLogEnabled = () =>
  parseEnvBoolean(process.env.REACT_APP_SOCKET_STOMP_DEBUG_LOGS);

export {
  deepEqual,
  isSocketMockEnabled,
  clearNullValues,
  isStompDebugLogEnabled,
};
