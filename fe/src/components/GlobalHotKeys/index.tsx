import { Card, Descriptions } from "antd";
import { useTranslation } from "react-i18next";
import { FBCardTitle } from "../common/CardTitle";

const GlobalHotKeys: React.FC = () => {
  const { t } = useTranslation();
  return (
    <Card className={"fb-card"}>
      <Card.Grid className={"fb-card-grid-item-100"} hoverable={false}>
        <FBCardTitle title={t("globalHotKeys.title")} />
      </Card.Grid>
      <Card.Grid className={"fb-card-grid-item-50"} hoverable={false}>
        <Descriptions size={"small"}>
          <Descriptions.Item label={t("globalHotKeys.addPipe")}>
            Alt + A
          </Descriptions.Item>
        </Descriptions>
      </Card.Grid>
      <Card.Grid className={"fb-card-grid-item-50"} hoverable={false}>
        <Descriptions size={"small"}>
          <Descriptions.Item label={t("globalHotKeys.removePipe")}>
            Alt + D
          </Descriptions.Item>
        </Descriptions>
      </Card.Grid>
    </Card>
  );
};

export default GlobalHotKeys;
