import { Button, Collapse, InputNumber, Switch, Table } from "antd";
import { ColumnsType } from "antd/es/table";
import { useMemo } from "react";
import { useTranslation } from "react-i18next";
import { FBCardTitle } from "../../common/CardTitle";
import { PlusCircleOutlined, DeleteOutlined } from "@ant-design/icons";
import { FBFeature } from "../../common/types";
import { Hotkey } from "../../../api/types";
import { InputColum } from "./InputColumn";
import { useSelector } from "react-redux";
import { selectors } from "../../../api/slice";
import {
  useAddHotkey,
  useUpdateHotkeyDelay,
  useUpdateHotkeyActive,
  useDeleteHotkey,
} from "../../../api/hooks/hotkeys";
import { useStatusTitles } from "../../common/hooks";
import { OverlayWrapper } from "../../common/OverlayWrapper";

const HotKeysLoop: React.FC<FBFeature> = ({ pipelineId, i }) => {
  const { t } = useTranslation();
  const hotkeysConfiguration = useSelector(
    selectors.hotkeysConfigurationSelector(pipelineId)
  );
  const status = useSelector(selectors.hotkeysStatusSelector(pipelineId));

  const addHotkey = useAddHotkey();
  const updateDelay = useUpdateHotkeyDelay();
  const updateActive = useUpdateHotkeyActive();
  const deleteHotkey = useDeleteHotkey();

  const columns = useMemo<ColumnsType<Hotkey>>(
    () => [
      {
        title: t("pipe.hotKeysLoop.keys.title"),
        key: "keys",
        render: (_, item) => <InputColum item={item} />,
      },
      {
        title: t("pipe.hotKeysLoop.delayMs"),
        key: "delayMs",
        width: 135,
        render: (_, item) => (
          <InputNumber
            value={item.delayMs}
            addonAfter={"ms"}
            onChange={(delayMs) =>
              updateDelay({ id: item.id, delayMs: delayMs ?? undefined })
            }
          />
        ),
      },
      {
        title: t("pipe.hotKeysLoop.active"),
        key: "active",
        width: 75,
        render: (_, item) => (
          <Switch
            defaultChecked={item.active}
            onChange={(active) => updateActive({ id: item.id, active })}
          />
        ),
      },
      {
        title: (
          <Button
            type="primary"
            icon={<PlusCircleOutlined />}
            onClick={() => addHotkey({ pipelineId })}
          />
        ),
        key: "actions",
        width: 50,
        render: (_, item) => (
          <Button
            type="text"
            icon={<DeleteOutlined />}
            danger
            onClick={() => deleteHotkey({ id: item.id })}
          />
        ),
      },
    ],
    [addHotkey, pipelineId, deleteHotkey, t, updateActive, updateDelay]
  );

  const titles = useStatusTitles(i);

  return (
    <Collapse ghost defaultActiveKey={"1"}>
      <Collapse.Panel
        header={
          <FBCardTitle
            title={t("pipe.hotKeysLoop.title")}
            statusIcon={status}
            statusTitles={titles}
            pipelineId={pipelineId}
          />
        }
        key="1"
        className="fb-collapse-padding-0 fb-card-title"
      >
        <OverlayWrapper pipelineId={pipelineId}>
          <Table
            columns={columns}
            dataSource={hotkeysConfiguration}
            size="small"
            pagination={false}
          />
        </OverlayWrapper>
      </Collapse.Panel>
    </Collapse>
  );
};

export default HotKeysLoop;
