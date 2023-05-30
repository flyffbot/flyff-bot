import { useMemo } from "react";
import { Trans } from "react-i18next";
import { ActionStatus } from "../../api/types";

export const usePausePipeShortcutLabel = (i: number) => {
  return useMemo(() => "Shift + " + (i + (i + 1)), [i]);
};

export const useCustomActionSlotLabel = (i: number) => {
  return useMemo(() => "Shift + " + (i + (i + 2)), [i]);
};

export const useStatusTitles = (i: number) => {
  const shortcut = usePausePipeShortcutLabel(i);

  return useMemo(() => {
    const map = new Map<ActionStatus, JSX.Element>();
    map.set(
      ActionStatus.PAUSED,
      <Trans i18nKey="common.status.paused" values={{ i: i + 1, shortcut }} />
    );
    map.set(
      ActionStatus.RUNNING,
      <Trans i18nKey="common.status.running" values={{ i: i + 1 }} />
    );
    return map;
  }, [shortcut, i]);
};
