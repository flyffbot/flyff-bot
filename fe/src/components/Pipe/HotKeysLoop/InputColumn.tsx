import { Row, Col, Select } from "antd";
import React, { useCallback, useMemo } from "react";
import { useTranslation } from "react-i18next";
import { useUpdateHotkeyHexValue } from "../../../api/hooks/hotkeys";
import { AuxCodes, Hotkey, KeyCodes } from "../../../api/types";

type InputColumArgs = {
  item: Hotkey;
};
export const InputColum: React.FC<InputColumArgs> = ({ item }) => {
  const { t } = useTranslation();
  const updateHexValue = useUpdateHotkeyHexValue();
  const prepareOptions = useCallback(
    (enumOptions: typeof AuxCodes | typeof KeyCodes) =>
      Object.values(enumOptions)
        .filter((value) => value !== "")
        .map((value) => ({ value, label: t(`common.keyCodes.${value}`) })),
    [t]
  );

  const auxKeysOptions = useMemo(
    () => prepareOptions(AuxCodes),
    [prepareOptions]
  );
  const keyCodesOptions = useMemo(
    () => prepareOptions(KeyCodes),
    [prepareOptions]
  );

  return (
    <Row justify={"space-around"}>
      <Col span={10}>
        <Select
          className={"width-100"}
          value={item.hexKeyCode0}
          onChange={(hexKeyCode) =>
            updateHexValue({ id: item.id, keyIndex: 0, hexKeyCode })
          }
          options={[
            {
              value: "",
              label: t("pipe.hotKeysLoop.keys.placeholder"),
              disabled: true,
            },
            ...auxKeysOptions,
          ]}
        />
      </Col>
      <Col span={10}>
        <Select
          className={"width-100"}
          value={item.hexKeyCode1}
          onChange={(hexKeyCode) =>
            updateHexValue({ id: item.id, keyIndex: 1, hexKeyCode })
          }
          options={[
            {
              value: "",
              label: t("pipe.hotKeysLoop.keys.placeholder"),
              disabled: true,
            },
            ...keyCodesOptions,
          ]}
        />
      </Col>
    </Row>
  );
};
