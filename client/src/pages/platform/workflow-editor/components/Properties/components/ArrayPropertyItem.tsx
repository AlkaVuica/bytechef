import {ControlTypeModel} from '@/shared/middleware/platform/configuration';
import {ArrayPropertyType, ComponentType, PropertyType} from '@/shared/types';

import Property from '../Property';
import DeletePropertyButton from './DeletePropertyButton';

interface ArrayPropertyItemProps {
    arrayItem: ArrayPropertyType;
    arrayName?: string;
    currentComponent?: ComponentType;
    index: number;
    onDeleteClick: (path: string, name: string, index: number) => void;
    path?: string;
    setArrayItems: React.Dispatch<React.SetStateAction<Array<ArrayPropertyType | Array<ArrayPropertyType>>>>;
}

const ArrayPropertyItem = ({
    arrayItem,
    arrayName,
    currentComponent,
    index,
    onDeleteClick,
    path,
    setArrayItems,
}: ArrayPropertyItemProps) => {
    const handleOnDeleteClick = () => {
        onDeleteClick(`${path}.${arrayName}`, arrayItem.name!, index);

        setArrayItems((subProperties) =>
            subProperties.filter((_subProperty, subPropertyIndex) => subPropertyIndex !== index)
        );
    };

    return (
        <div className="ml-2 flex w-full items-center" key={arrayItem.name}>
            <Property
                arrayIndex={index}
                arrayName={arrayName}
                customClassName="pl-2 w-full"
                inputTypeSwitchButtonClassName="ml-auto"
                parameterValue={arrayItem.defaultValue}
                path={`${path}.${arrayName}`}
                property={arrayItem as PropertyType & {controlType?: ControlTypeModel; defaultValue?: string}}
            />

            {arrayItem.custom && arrayName && arrayItem.name && currentComponent && (
                <DeletePropertyButton
                    className="ml-2 mr-4"
                    currentComponent={currentComponent}
                    onClick={handleOnDeleteClick}
                    propertyName={arrayName}
                    subPropertyIndex={index}
                />
            )}
        </div>
    );
};

export default ArrayPropertyItem;
