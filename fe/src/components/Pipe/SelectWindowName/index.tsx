import { Select } from "antd";
import { useEffect, useMemo } from "react";
import { useTranslation } from "react-i18next";
import { useSelector } from "react-redux";
import { useUpdateSelectedWindow } from "../../../api/hooks/pipeline";
import { selectors } from "../../../api/slice";
import { FBCardTitle } from "../../common/CardTitle";
import { FBFeature } from "../../common/types";

const SelectWindowName: React.FC<FBFeature> = ({ pipelineId }) => {
  const { t } = useTranslation();
  const pipelineConfiguration = useSelector(
    selectors.pipelineConfigurationSelector(pipelineId)
  );
  const windowList = useSelector(selectors.windowsSelector);
  const isSelectedAndExists = useSelector(
    selectors.isSelectedAndExistsSelector(pipelineId)
  );

  const selectedWindowHwnd = useMemo(
    () => pipelineConfiguration?.selectedWindowHwnd,
    [pipelineConfiguration?.selectedWindowHwnd]
  );

  const { updateSelectedWindow } = useUpdateSelectedWindow();

  const options = useMemo(
    () =>
      windowList.map(({ hwnd: value, title: label }) => ({ value, label })) ??
      [],
    [windowList]
  );

  useEffect(() => {
    if (isSelectedAndExists) {
      // Nothing to unselect, it's already undefined!
      return;
    }
    updateSelectedWindow({ pipelineId, hwnd: undefined });
  }, [pipelineId, isSelectedAndExists, updateSelectedWindow]);

  return (
    <>
      <FBCardTitle title={t("pipe.selectWindowName.title")} />
      <Select
        placeholder={t("pipe.selectWindowName.placeholder")}
        value={selectedWindowHwnd}
        className={"width-100"}
        onChange={(hwnd) => updateSelectedWindow({ pipelineId, hwnd })}
        options={options}
      />
    </>
  );
};

export default SelectWindowName;
