import DatePicker from '@/components/DatePicker/DatePicker';
import EmptyList from '@/components/EmptyList/EmptyList';
import FilterableSelect, {
    ISelectOption,
} from '@/components/FilterableSelect/FilterableSelect';
import PageLoader from '@/components/PageLoader/PageLoader';
import Pagination from '@/components/Pagination/Pagination';
import LayoutContainer from '@/layouts/LayoutContainer';
import PageFooter from '@/layouts/PageFooter';
import PageHeader from '@/layouts/PageHeader';
import {QueueListIcon} from '@heroicons/react/24/outline';
import {
    ExecutionModel,
    ExecutionModelFromJSON,
    GetExecutionsJobStatusEnum,
} from 'middleware/helios/execution';
import {
    useGetExecutionsQuery,
    useGetProjectInstancesQuery,
    useGetProjectsQuery,
    useGetWorkflowsQuery,
} from 'queries/projects.queries';
import {useState} from 'react';
import {OnChangeValue} from 'react-select';
import {twMerge} from 'tailwind-merge';

import useWorkflowExecutionDetailsDialogStore from '../project/stores/useWorkflowExecutionDetailsDialogStore';
import ExecutionDetailsDialog from './components/ExecutionDetailsDialog';
import ExecutionsTable from './components/ExecutionsTable';

const jobStatusOptions = [
    {
        label: GetExecutionsJobStatusEnum.Started,
        value: GetExecutionsJobStatusEnum.Started,
    },
    {
        label: GetExecutionsJobStatusEnum.Completed,
        value: GetExecutionsJobStatusEnum.Completed,
    },
    {
        label: GetExecutionsJobStatusEnum.Created,
        value: GetExecutionsJobStatusEnum.Created,
    },
    {
        label: GetExecutionsJobStatusEnum.Stopped,
        value: GetExecutionsJobStatusEnum.Stopped,
    },
    {
        label: GetExecutionsJobStatusEnum.Failed,
        value: GetExecutionsJobStatusEnum.Failed,
    },
];

export const Executions = () => {
    const [filterStatus, setFilterStatus] =
        useState<GetExecutionsJobStatusEnum>();
    const [filterStartDate, setFilterStartDate] = useState<Date | undefined>(
        undefined
    );
    const [filterEndDate, setFilterEndDate] = useState<Date | undefined>(
        undefined
    );
    const [filterProjectId, setFilterProjectId] = useState<number>();
    const [filterWorkflowId, setFilterWorkflowId] = useState<string>();
    const [filterInstanceId, setFilterInstanceId] = useState<number>();
    const [filterPageNumber, setFilterPageNumber] = useState<number>();

    const {data: projectInstances} = useGetProjectInstancesQuery({});

    const {
        data: projects,
        error: projectsError,
        isLoading: projectsLoading,
    } = useGetProjectsQuery({});

    const {
        data: WorkflowExecutionsPage,
        error: WorkflowExecutionsError,
        isLoading: WorkflowExecutionsLoading,
    } = useGetExecutionsQuery({
        jobEndDate: filterEndDate,
        jobStartDate: filterStartDate,
        jobStatus: filterStatus,
        pageNumber: filterPageNumber,
        projectId: filterProjectId,
        projectInstanceId: filterInstanceId,
        workflowId: filterWorkflowId,
    });

    const {data: workflows, error: workflowsError} = useGetWorkflowsQuery();

    const {workflowExecutionDetailsDialogOpen} =
        useWorkflowExecutionDetailsDialogStore();

    const emptyListMessage =
        !filterStatus &&
        !filterProjectId &&
        !filterWorkflowId &&
        !filterStartDate &&
        !filterEndDate &&
        !filterInstanceId &&
        !filterPageNumber
            ? "You don't have any executed workflows yet."
            : 'There is no executed workflows for the current criteria.';

    const tableData = WorkflowExecutionsPage?.content?.map(
        (WorkflowExecution: ExecutionModel) =>
            ExecutionModelFromJSON(WorkflowExecution)
    );

    return (
        <PageLoader
            errors={[projectsError, workflowsError]}
            loading={projectsLoading}
        >
            <LayoutContainer
                footer={
                    WorkflowExecutionsPage?.content &&
                    WorkflowExecutionsPage.content.length > 0 && (
                        <PageFooter position="main">
                            <Pagination
                                pageNumber={WorkflowExecutionsPage.number!}
                                pageSize={WorkflowExecutionsPage.size!}
                                totalElements={
                                    WorkflowExecutionsPage.totalElements!
                                }
                                totalPages={WorkflowExecutionsPage.totalPages!}
                                onClick={setFilterPageNumber}
                            />
                        </PageFooter>
                    )
                }
                header={
                    <PageHeader
                        position="main"
                        title="All Workflow Executions"
                    />
                }
                leftSidebarHeader={
                    <>
                        <PageHeader
                            position="sidebar"
                            title="Workflow Executions"
                        />

                        <div className="px-4">
                            <FilterableSelect
                                isClearable
                                label="Status"
                                name="jobStatus"
                                options={jobStatusOptions}
                                onChange={(
                                    value: OnChangeValue<ISelectOption, false>
                                ) => {
                                    if (value) {
                                        setFilterStatus(
                                            value.value as GetExecutionsJobStatusEnum
                                        );
                                    } else {
                                        setFilterStatus(undefined);
                                    }
                                }}
                            />

                            <DatePicker
                                label="Start date"
                                name="jobStartDate"
                                placeholder="Select..."
                                onChange={setFilterStartDate}
                            />

                            <DatePicker
                                label="End date"
                                name="jobEndDate"
                                placeholder="Select..."
                                onChange={setFilterEndDate}
                            />

                            {projects?.length && (
                                <FilterableSelect
                                    isClearable
                                    label="Projects"
                                    name="project"
                                    options={projects?.map((project) => ({
                                        label: project.name,
                                        value: (project.id || 0).toString(),
                                    }))}
                                    onChange={(
                                        value: OnChangeValue<
                                            ISelectOption,
                                            false
                                        >
                                    ) => {
                                        if (value) {
                                            setFilterProjectId(
                                                Number(value.value)
                                            );
                                        } else {
                                            setFilterProjectId(undefined);
                                        }
                                    }}
                                />
                            )}

                            {workflows?.length && (
                                <FilterableSelect
                                    isClearable
                                    label="Workflows"
                                    name="workflows"
                                    options={workflows?.map((workflow) => ({
                                        label:
                                            workflow.label || 'undefined label',
                                        value: (workflow.id || 0).toString(),
                                    }))}
                                    onChange={(
                                        value: OnChangeValue<
                                            ISelectOption,
                                            false
                                        >
                                    ) => {
                                        if (value) {
                                            setFilterWorkflowId(value.value);
                                        } else {
                                            setFilterWorkflowId(undefined);
                                        }
                                    }}
                                />
                            )}

                            {projectInstances &&
                                projectInstances?.length > 0 && (
                                    <FilterableSelect
                                        isClearable
                                        label="Instances"
                                        name="instances"
                                        options={projectInstances?.map(
                                            (instance) => ({
                                                label: instance.name,
                                                value: (
                                                    instance.id || 0
                                                ).toString(),
                                            })
                                        )}
                                        onChange={(
                                            value: OnChangeValue<
                                                ISelectOption,
                                                false
                                            >
                                        ) =>
                                            value
                                                ? setFilterInstanceId(
                                                      Number(value.value)
                                                  )
                                                : setFilterInstanceId(undefined)
                                        }
                                    />
                                )}
                        </div>
                    </>
                }
            >
                {!WorkflowExecutionsLoading &&
                    !WorkflowExecutionsError &&
                    WorkflowExecutionsPage?.content && (
                        <div
                            className={twMerge(
                                'w-full px-4 2xl:mx-auto 2xl:w-4/5',
                                !WorkflowExecutionsPage.content.length &&
                                    'place-self-center'
                            )}
                        >
                            {tableData && tableData.length > 0 ? (
                                <ExecutionsTable data={tableData} />
                            ) : (
                                <EmptyList
                                    icon={
                                        <QueueListIcon className="h-12 w-12 text-gray-400" />
                                    }
                                    message={emptyListMessage}
                                    title="No executed workflows"
                                />
                            )}
                        </div>
                    )}

                {workflowExecutionDetailsDialogOpen && (
                    <ExecutionDetailsDialog />
                )}
            </LayoutContainer>
        </PageLoader>
    );
};

export default Executions;