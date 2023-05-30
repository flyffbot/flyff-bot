import { Card } from "antd";
import { useTranslation } from "react-i18next";
import { FBCardTitle } from "../common/CardTitle";
import HotKeysLoop from "./HotKeysLoop";
import DefaultHotKeys from "./DefaultHotKeys";
import SelectWindowName from "./SelectWindowName";
import CustomActionSlot from "./CustomActionSlot";
import { useSelector } from "react-redux";
import { selectors } from "../../api/slice";
import { ActionStatus } from "../../api/types";
import { useStatusTitles } from "../common/hooks";

interface PipeProps {
  id: number;
  i: number;
}

const Pipe: React.FC<PipeProps> = ({ id, i }) => {
  const { t } = useTranslation();
  const pipelineData = useSelector(selectors.pipelineConfigurationSelector(id));

  const titles = useStatusTitles(i);

  return (
    <Card className={"fb-card"}>
      <Card.Grid className={"fb-card-grid-item-100"} hoverable={false}>
        <FBCardTitle
          title={t("pipe.title", { i })}
          statusIcon={
            pipelineData?.paused ? ActionStatus.PAUSED : ActionStatus.RUNNING
          }
          statusTitles={titles}
          pipelineId={id}
        />
      </Card.Grid>
      <Card.Grid className={"fb-card-grid-item-100"} hoverable={false}>
        <SelectWindowName pipelineId={id} i={i} />
      </Card.Grid>
      <Card.Grid className={"fb-card-grid-item-100"} hoverable={false}>
        <DefaultHotKeys pipelineId={id} i={i} />
      </Card.Grid>
      <Card.Grid className={"fb-card-grid-item-100"} hoverable={false}>
        <HotKeysLoop pipelineId={id} i={i} />
      </Card.Grid>
      <Card.Grid className={"fb-card-grid-item-100"} hoverable={false}>
        <CustomActionSlot pipelineId={id} i={i} />
      </Card.Grid>
    </Card>
  );
};

export default Pipe;
