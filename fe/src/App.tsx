import React, { useMemo, useState } from "react";
import { Layout, Row, Col, Card } from "antd";
import { Content, Footer } from "antd/es/layout/layout";
import GlobalHotKeys from "./components/GlobalHotKeys";
import "./App.css";
import Pipe from "./components/Pipe";
import { selectors } from "./api/slice";
import { useInitSocket } from "./api/hooks/app";
import { useSelector } from "react-redux";
import { FooterContent } from "./components/common/FooterContent";
import { ActiveNode } from "./components/common/types";
import { useTranslation } from "react-i18next";
const App: React.FC = () => {
  const { t } = useTranslation();
  useInitSocket();
  const pipelines = useSelector(selectors.pipelinesSelector);
  const [pipelinesTabVisible, setPipelinesTabVisible] = useState(true);

  const pipelinesTab = useMemo(
    () => (
      <>
        <Row className="fb-padding-top fb-padding-left">
          <Col>
            <GlobalHotKeys />
          </Col>
        </Row>
        <Row>
          {pipelines.map((configuration, i) => (
            <Col
              className="fb-padding-top fb-padding-left"
              key={configuration.pipeline.id}
            >
              <Pipe id={configuration.pipeline.id} i={i} />
            </Col>
          ))}
        </Row>
      </>
    ),
    [pipelines]
  );

  const helpTab = useMemo(
    () => (
      <div className="stack-vertical stack-justify-space-around height-100">
        <div className="stack-horizontal stack-justify-space-around">
          <Card title={t("tabs.help.readyToGo")} bordered={false}>
            <video src="youtu.be/MLeIBFYY6UY" controls></video>
          </Card>
          <Card title={t("tabs.help.addRemovePipe")} bordered={false}>
            <video src="youtu.be/MLeIBFYY6UY" controls></video>
          </Card>
        </div>
        <div className="stack-horizontal stack-justify-space-around">
          <Card title={t("tabs.help.hotkeysLoop")} bordered={false}>
            <video src="youtu.be/MLeIBFYY6UY" controls></video>
          </Card>
          <Card title={t("tabs.help.customActionSlot")} bordered={false}>
            <video src="youtu.be/MLeIBFYY6UY" controls></video>
          </Card>
        </div>
      </div>
    ),
    [t]
  );

  return (
    <Layout className="height-100-vh">
      <Content className="overflow-auto">
        {pipelinesTabVisible ? pipelinesTab : helpTab}
      </Content>
      <Footer className="fb-footer">
        <FooterContent
          onChangeTab={(tab) =>
            setPipelinesTabVisible(tab === ActiveNode.PIPELINE)
          }
        />
      </Footer>
    </Layout>
  );
};

export default App;
