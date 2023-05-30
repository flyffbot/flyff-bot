import {
  PauseCircleOutlined,
  LoadingOutlined
} from '@ant-design/icons';

interface FBSpinnerArgs {
  paused?:boolean
}

const FBSpinner:React.FC<FBSpinnerArgs> = ({paused}) => paused === false ? <LoadingOutlined /> : <PauseCircleOutlined />

export default FBSpinner;

