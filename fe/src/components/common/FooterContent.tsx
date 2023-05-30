import { Button, Col, Row } from "antd";
import { RobotOutlined, QuestionCircleOutlined } from "@ant-design/icons";
import { useEffect, useState } from "react";
import { ActiveNode } from "./types";
import { useTranslation } from "react-i18next";

type FooterContentProps = {
  onChangeTab: (activeTab: ActiveNode) => void;
};

export const FooterContent: React.FC<FooterContentProps> = ({
  onChangeTab,
}) => {
  const { t } = useTranslation();
  const [active, setActive] = useState(ActiveNode.PIPELINE);

  useEffect(() => onChangeTab(active), [onChangeTab, active]);

  return (
    <Row justify={"center"} gutter={16}>
      <Col>
        <Button
          className="fb-footer-button"
          type={active === ActiveNode.PIPELINE ? "primary" : "default"}
          icon={<RobotOutlined />}
          onClick={() => setActive(ActiveNode.PIPELINE)}
        >
          {t("tabs.pipelines.title")}
        </Button>
      </Col>
      <Col>
        <Button
          className="fb-footer-button"
          type={active === ActiveNode.HELP ? "primary" : "default"}
          icon={<QuestionCircleOutlined />}
          onClick={() => setActive(ActiveNode.HELP)}
        >
          {t("tabs.help.title")}
        </Button>
      </Col>
    </Row>
  );
};
