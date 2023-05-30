import { Button, Col, Collapse, InputNumber, Row, Select, Table } from "antd";
import { ColumnsType } from "antd/es/table";
import { useCallback, useMemo } from "react";
import { useTranslation } from "react-i18next";
import { FBFeature } from "../../common/types";
import { PlusCircleOutlined, DeleteOutlined } from "@ant-design/icons";
import { AuxCodes, CustomActionSlotItem, KeyCodes } from "../../../api/types";
import { useSelector } from "react-redux";
import { selectors } from "../../../api/slice";
import {
  useAddCustomActionSlot,
  useUpdateCustomActionSlotHexValue,
  useUpdateCustomActionSlotCastTime,
  useDeleteCustomActionSlot,
} from "../../../api/hooks/customActionSlots";
import { FBCardTitle } from "../../common/CardTitle";
import { useStatusTitles } from "../../common/hooks";
import { OverlayWrapper } from "../../common/OverlayWrapper";

const CustomActionSlot: React.FC<FBFeature> = ({ pipelineId, i }) => {
  const { t } = useTranslation();
  const customActionSlotsConfiguration = useSelector(
    selectors.customActionSlotsConfigurationSelector(pipelineId)
  );
  const customActionSlotCount = useSelector(
    selectors.customActionSlotCountSelector(pipelineId)
  );
  const customActionSlotStatus = useSelector(
    selectors.customActionSlotStatusSelector(pipelineId)
  );

  const addCustomActionSlot = useAddCustomActionSlot();
  const updateCustomActionSlotHexValue = useUpdateCustomActionSlotHexValue();
  const updateCustomActionSlotCastTime = useUpdateCustomActionSlotCastTime();
  const deleteCustomActionSlot = useDeleteCustomActionSlot();

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

  const columns = useMemo<ColumnsType<CustomActionSlotItem>>(
    () => [
      {
        title: t("pipe.customActionSlot.keys.title"),
        key: "keys",
        render: (_, item) => (
          <Row justify={"space-around"}>
            <Col span={10}>
              <Select
                className={"width-100"}
                value={item.hexKeyCode0}
                onChange={(hexKeyCode) =>
                  updateCustomActionSlotHexValue({
                    id: item.id,
                    keyIndex: 0,
                    hexKeyCode,
                  })
                }
                options={[
                  {
                    value: "",
                    label: t("pipe.customActionSlot.keys.placeholder"),
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
                  updateCustomActionSlotHexValue({
                    id: item.id,
                    keyIndex: 1,
                    hexKeyCode,
                  })
                }
                options={[
                  {
                    value: "",
                    label: t("pipe.customActionSlot.keys.placeholder"),
                    disabled: true,
                  },
                  ...keyCodesOptions,
                ]}
              />
            </Col>
          </Row>
        ),
      },
      {
        title: t("pipe.customActionSlot.castTimeMs"),
        key: "castTimeMs",
        width: 210,
        render: (_, item) => (
          <InputNumber
            value={item.castTimeMs}
            addonAfter={"ms"}
            onChange={(castTimeMs) =>
              updateCustomActionSlotCastTime({
                id: item.id,
                castTimeMs: castTimeMs ?? undefined,
              })
            }
          />
        ),
      },
      {
        title: (
          <Button
            type="primary"
            icon={<PlusCircleOutlined />}
            onClick={() => addCustomActionSlot({ pipelineId })}
          />
        ),
        key: "actions",
        width: 50,
        render: (_, item) => (
          <Button
            type="text"
            icon={<DeleteOutlined />}
            danger
            onClick={() => deleteCustomActionSlot({ id: item.id })}
          />
        ),
      },
    ],
    [
      addCustomActionSlot,
      auxKeysOptions,
      deleteCustomActionSlot,
      keyCodesOptions,
      pipelineId,
      t,
      updateCustomActionSlotCastTime,
      updateCustomActionSlotHexValue,
    ]
  );

  const titles = useStatusTitles(i);

  return (
    <Collapse ghost>
      <Collapse.Panel
        header={
          <FBCardTitle
            title={t("pipe.customActionSlot.title", {
              count: customActionSlotCount,
            })}
            statusIcon={customActionSlotStatus}
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
            dataSource={customActionSlotsConfiguration}
            size="small"
            pagination={false}
          />
        </OverlayWrapper>
      </Collapse.Panel>
    </Collapse>
  );
};

export default CustomActionSlot;
