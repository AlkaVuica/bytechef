import {
    AlertDialog,
    AlertDialogAction,
    AlertDialogCancel,
    AlertDialogContent,
    AlertDialogDescription,
    AlertDialogFooter,
    AlertDialogHeader,
    AlertDialogTitle,
} from '@/components/ui/alert-dialog';
import {Button} from '@/components/ui/button';
import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuSeparator,
    DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import {Tooltip, TooltipContent, TooltipTrigger} from '@/components/ui/tooltip';
import {IntegrationModel, WorkflowModel} from '@/middleware/embedded/configuration';
import {ComponentDefinitionBasicModel} from '@/middleware/platform/configuration';
import {useDeleteWorkflowMutation, useUpdateWorkflowMutation} from '@/mutations/embedded/workflows.mutations';
import WorkflowDialog from '@/pages/platform/workflow/components/WorkflowDialog';
import {useGetWorkflowQuery} from '@/queries/automation/workflows.queries';
import {IntegrationWorkflowKeys} from '@/queries/embedded/integrationWorkflows.queries';
import {IntegrationKeys} from '@/queries/embedded/integrations.queries';
import {WorkflowKeys} from '@/queries/embedded/workflows.queries';
import {WorkflowTestConfigurationKeys} from '@/queries/platform/workflowTestConfigurations.queries';
import {DotsVerticalIcon} from '@radix-ui/react-icons';
import {useQueryClient} from '@tanstack/react-query';
import {useState} from 'react';
import InlineSVG from 'react-inlinesvg';
import {Link} from 'react-router-dom';

const IntegrationWorkflowListItem = ({
    filteredComponentNames,
    integration,
    workflow,
    workflowComponentDefinitions,
    workflowTaskDispatcherDefinitions,
}: {
    filteredComponentNames?: string[];
    integration: IntegrationModel;
    workflow: WorkflowModel;
    workflowComponentDefinitions: {
        [key: string]: ComponentDefinitionBasicModel | undefined;
    };
    workflowTaskDispatcherDefinitions: {
        [key: string]: ComponentDefinitionBasicModel | undefined;
    };
}) => {
    const [showDeleteDialog, setShowDeleteDialog] = useState(false);
    const [showEditDialog, setShowEditDialog] = useState(false);

    const queryClient = useQueryClient();

    const deleteWorkflowMutation = useDeleteWorkflowMutation({
        onSuccess: () => {
            queryClient.invalidateQueries({queryKey: IntegrationKeys.integrations});

            setShowDeleteDialog(false);
        },
    });

    const updateWorkflowMutation = useUpdateWorkflowMutation({
        onSuccess: (workflow) => {
            queryClient.invalidateQueries({
                queryKey: IntegrationWorkflowKeys.integrationWorkflows(integration.id!),
            });

            queryClient.invalidateQueries({
                queryKey: WorkflowTestConfigurationKeys.workflowTestConfiguration(workflow.id!),
            });

            queryClient.invalidateQueries({
                queryKey: WorkflowKeys.workflow(workflow.id!),
            });

            setShowEditDialog(false);
        },
    });

    return (
        <>
            <Link
                className="flex flex-1 items-center"
                to={`/embedded/integrations/${integration.id}/integration-workflows/${workflow.integrationWorkflowId}`}
            >
                <div className="w-96 text-sm font-semibold">{workflow.label}</div>

                <div className="flex">
                    {filteredComponentNames?.map((name) => {
                        const componentDefinition = workflowComponentDefinitions[name];
                        const taskDispatcherDefinition = workflowTaskDispatcherDefinitions[name];

                        return (
                            <div className="mr-0.5 flex items-center justify-center rounded-full border p-1" key={name}>
                                <Tooltip>
                                    <TooltipTrigger>
                                        <InlineSVG
                                            className="size-5 flex-none"
                                            key={name}
                                            src={
                                                componentDefinition?.icon
                                                    ? componentDefinition?.icon
                                                    : taskDispatcherDefinition?.icon ?? ''
                                            }
                                        />
                                    </TooltipTrigger>

                                    <TooltipContent side="top">{componentDefinition?.title}</TooltipContent>
                                </Tooltip>
                            </div>
                        );
                    })}
                </div>
            </Link>

            <div className="flex justify-end gap-x-4">
                <Tooltip>
                    <TooltipTrigger className="flex items-center text-sm text-gray-500">
                        <span>
                            {`Modified at ${workflow.lastModifiedDate?.toLocaleDateString()} ${workflow.lastModifiedDate?.toLocaleTimeString()}`}
                        </span>
                    </TooltipTrigger>

                    <TooltipContent>Last Modified Date</TooltipContent>
                </Tooltip>

                <DropdownMenu>
                    <DropdownMenuTrigger asChild>
                        <Button size="icon" variant="ghost">
                            <DotsVerticalIcon className="size-4 hover:cursor-pointer" />
                        </Button>
                    </DropdownMenuTrigger>

                    <DropdownMenuContent align="end">
                        <DropdownMenuItem
                            onClick={() => {
                                setShowEditDialog(true);
                            }}
                        >
                            Edit
                        </DropdownMenuItem>

                        <DropdownMenuItem
                            onClick={() => (window.location.href = `/api/embedded/workflows/${workflow.id}/export`)}
                        >
                            Export
                        </DropdownMenuItem>

                        <DropdownMenuSeparator />

                        <DropdownMenuItem
                            className="text-red-600"
                            onClick={() => {
                                setShowDeleteDialog(true);
                            }}
                        >
                            Delete
                        </DropdownMenuItem>
                    </DropdownMenuContent>
                </DropdownMenu>
            </div>

            <AlertDialog open={showDeleteDialog}>
                <AlertDialogContent>
                    <AlertDialogHeader>
                        <AlertDialogTitle>Are you absolutely sure?</AlertDialogTitle>

                        <AlertDialogDescription>
                            This action cannot be undone. This will permanently delete the workflow.
                        </AlertDialogDescription>
                    </AlertDialogHeader>

                    <AlertDialogFooter>
                        <AlertDialogCancel onClick={() => setShowDeleteDialog(false)}>Cancel</AlertDialogCancel>

                        <AlertDialogAction
                            className="bg-red-600"
                            onClick={() => {
                                if (workflow?.id) {
                                    deleteWorkflowMutation.mutate({
                                        id: workflow?.id,
                                    });
                                }
                            }}
                        >
                            Delete
                        </AlertDialogAction>
                    </AlertDialogFooter>
                </AlertDialogContent>
            </AlertDialog>

            {showEditDialog && workflow && (
                <WorkflowDialog
                    onClose={() => setShowEditDialog(false)}
                    updateWorkflowMutation={updateWorkflowMutation}
                    useGetWorkflowQuery={useGetWorkflowQuery}
                    workflowId={workflow.id!}
                />
            )}
        </>
    );
};

export default IntegrationWorkflowListItem;
