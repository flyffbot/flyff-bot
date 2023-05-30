import { Col, Row } from "antd";
import { useTranslation } from "react-i18next";
import { useSelector } from "react-redux";
import { selectors } from "../../api/slice";

type OverlayWrapperProps = {
  children?: JSX.Element;
  pipelineId: number;
};

export const OverlayWrapper: React.FC<OverlayWrapperProps> = ({
  children,
  pipelineId,
}) => {
  const { t } = useTranslation();
  const isHwndValid = useSelector(
    selectors.isSelectedAndExistsSelector(pipelineId)
  );

  if (isHwndValid) {
    return <>{children}</>;
  }

  return (
    <div className="fb-overlay-wrapper">
      <div className="fb-overlay-content">
        <Row
          justify={"center"}
          align="middle"
          className={"height-100 color-red-5"}
        >
          <Col>{t("common.nohwnd")}</Col>
        </Row>
      </div>
      {children}
    </div>
  );
};
