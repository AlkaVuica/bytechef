import DropdownMenu from '@/components/DropdownMenu/DropdownMenu';
import {Button} from '@/components/ui/button';
import {
    Tooltip,
    TooltipContent,
    TooltipProvider,
    TooltipTrigger,
} from '@/components/ui/tooltip';
import {ComponentDefinitionBasicModel} from '@/middleware/core/workflow/configuration';
import WorkflowDialog from 'components/WorkflowDialog/WorkflowDialog';
import {ProjectModel} from 'middleware/automation/configuration';
import {useCreateProjectWorkflowRequestMutation} from 'mutations/projects.mutations';
import {useGetComponentDefinitionsQuery} from 'queries/componentDefinitions.queries';
import {useGetProjectWorkflowsQuery} from 'queries/projects.queries';
import {useState} from 'react';
import InlineSVG from 'react-inlinesvg';
import {Link, useNavigate} from 'react-router-dom';

const ProjectWorkflowList = ({project}: {project: ProjectModel}) => {
    const {data: workflows} = useGetProjectWorkflowsQuery(project.id!);

    const [showWorkflowDialog, setShowWorkflowDialog] = useState(false);

    const navigate = useNavigate();

    const createProjectWorkflowRequestMutation =
        useCreateProjectWorkflowRequestMutation({
            onSuccess: (workflow) => {
                navigate(
                    `/automation/projects/${project.id}/workflow/${workflow?.id}`
                );

                setShowWorkflowDialog(false);
            },
        });

    const {data: componentDefinitions} = useGetComponentDefinitionsQuery();

    const workflowComponentDefinitions: {
        [key: string]: ComponentDefinitionBasicModel | undefined;
    } = {};

    workflows?.map((workflow) => {
        const componentNames = workflow.tasks?.map(
            (task) => task.type.split('/')[0]
        );

        componentNames?.map((componentName) => {
            if (!workflowComponentDefinitions[componentName]) {
                workflowComponentDefinitions[componentName] =
                    componentDefinitions?.find(
                        (componentDefinition) =>
                            componentDefinition.name === componentName
                    );
            }
        });
    });

    return (
        <div className="border-b border-b-gray-100 py-2">
            <div className="mb-1 flex items-center justify-between">
                <h3 className="flex justify-start pl-2 text-sm font-semibold uppercase text-gray-500">
                    Workflows
                </h3>

                <div className="flex justify-end">
                    <Button
                        className="flex justify-end"
                        size="sm"
                        variant="secondary"
                        onClick={() => {
                            setShowWorkflowDialog(true);
                        }}
                    >
                        New Workflow
                    </Button>
                </div>
            </div>

            <ul>
                {workflows?.map((workflow) => {
                    let componentNames = workflow.tasks?.map(
                        (task) => task.type.split('/')[0]
                    );

                    componentNames = componentNames?.filter(
                        (item, index) => componentNames?.indexOf(item) === index
                    );

                    return (
                        <li
                            key={workflow.id}
                            className="flex items-center justify-between rounded-md p-2 hover:bg-gray-50"
                        >
                            <div className="w-10/12">
                                <Link
                                    className="flex items-center"
                                    to={`/automation/projects/${project.id}/workflow/${workflow.id}`}
                                >
                                    <div className="w-6/12 text-sm font-semibold">
                                        {workflow.label}
                                    </div>

                                    <div className="ml-6 flex">
                                        {componentNames?.map(
                                            (componentName) => {
                                                const componentDefinition =
                                                    workflowComponentDefinitions[
                                                        componentName
                                                    ];
                                                return (
                                                    <div
                                                        key={componentName}
                                                        className="mr-0.5 flex items-center justify-center rounded-full border p-1"
                                                    >
                                                        <TooltipProvider>
                                                            <Tooltip>
                                                                <TooltipTrigger>
                                                                    <InlineSVG
                                                                        className="h-5 w-5 flex-none"
                                                                        key={
                                                                            componentName
                                                                        }
                                                                        src={
                                                                            componentDefinition?.icon ??
                                                                            ''
                                                                        }
                                                                    />
                                                                </TooltipTrigger>

                                                                <TooltipContent side="right">
                                                                    {
                                                                        componentDefinition?.title
                                                                    }
                                                                </TooltipContent>
                                                            </Tooltip>
                                                        </TooltipProvider>
                                                    </div>
                                                );
                                            }
                                        )}
                                    </div>

                                    <div className="flex flex-1 justify-end text-sm">
                                        {project.lastModifiedDate?.toLocaleDateString()}
                                    </div>
                                </Link>
                            </div>

                            <DropdownMenu
                                id={project.id}
                                menuItems={[
                                    {
                                        label: 'Edit',
                                        onClick: () => {
                                            console.log('TODO');
                                        },
                                    },
                                    {
                                        label: 'Duplicate',
                                        onClick: () => {
                                            console.log('TODO');
                                        },
                                    },
                                    {
                                        separator: true,
                                    },
                                    {
                                        danger: true,
                                        label: 'Delete',
                                        onClick: () => {
                                            console.log('TODO');
                                        },
                                    },
                                ]}
                            />
                        </li>
                    );
                })}
            </ul>

            {showWorkflowDialog && !!project.id && (
                <WorkflowDialog
                    id={project.id}
                    showTrigger={false}
                    visible
                    createWorkflowRequestMutation={
                        createProjectWorkflowRequestMutation
                    }
                />
            )}
        </div>
    );
};

export default ProjectWorkflowList;