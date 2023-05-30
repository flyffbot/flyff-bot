import { PauseCircleOutlined, LoadingOutlined } from "@ant-design/icons";
import { Col, Row, Tooltip } from "antd";
import { useMemo } from "react";
import { useSelector } from "react-redux";
import { selectors } from "../../api/slice";
import { ActionStatus } from "../../api/types";

interface FBCardTitleProps {
  title: string;
  statusIcon?: ActionStatus;
  statusTitles?: Map<ActionStatus, JSX.Element>;
  pipelineId?: number;
}

export const FBCardTitle: React.FC<FBCardTitleProps> = ({
  title,
  statusIcon = ActionStatus.INVISIBLE,
  statusTitles,
  pipelineId,
}) => {
  const titleNode = useMemo(
    () => <div className="fb-card-title">{title}</div>,
    [title]
  );

  const isValidHwnd = useSelector(
    selectors.isSelectedAndExistsSelector(pipelineId)
  );

  if (statusIcon === ActionStatus.INVISIBLE || !isValidHwnd || !pipelineId) {
    return titleNode;
  }

  return (
    <Row justify={"space-between"}>
      <Col className="fb-card-title">{title}</Col>
      <Col
        className={
          statusIcon === ActionStatus.PAUSED ? "color-red-5" : "color-blue-5"
        }
      >
        <Tooltip placement="left" title={statusTitles?.get(statusIcon)}>
          {statusIcon === ActionStatus.PAUSED ? (
            <PauseCircleOutlined />
          ) : (
            <LoadingOutlined />
          )}
        </Tooltip>
      </Col>
    </Row>
  );
};
