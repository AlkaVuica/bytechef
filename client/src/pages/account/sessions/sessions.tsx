import {useAppDispatch, useAppSelector} from 'app/config/store';
import {getSession} from 'app/shared/reducers/authentication';
import React, {useEffect} from 'react';
import {Translate} from 'react-jhipster';
import {Alert, Button, Table} from 'reactstrap';

import {findAll, invalidateSession} from './sessions.reducer';

export const SessionsPage = () => {
    const dispatch = useAppDispatch();

    useEffect(() => {
        dispatch(getSession());
        dispatch(findAll());
    }, []);

    const doSessionInvalidation = (series) => () => {
        dispatch(invalidateSession(series));
        dispatch(findAll());
    };

    const refreshList = () => {
        dispatch(findAll());
    };

    const account = useAppSelector((state) => state.authentication.account);
    const sessions = useAppSelector((state) => state.sessions.sessions);
    const updateSuccess = useAppSelector((state) => state.sessions.updateSuccess);
    const updateFailure = useAppSelector((state) => state.sessions.updateFailure);

    return (
        <div>
            <h2>
                <Translate contentKey="sessions.title" interpolate={{username: account.login}}>
                    Active sessions for [<strong>{account.login}</strong>]
                </Translate>
            </h2>

            {updateSuccess ? (
                <Alert color="success">
                    <Translate contentKey="sessions.messages.success">
                        <strong>Session invalidated!</strong>
                    </Translate>
                </Alert>
            ) : null}

            {updateFailure ? (
                <Alert color="danger">
                    <Translate contentKey="sessions.messages.error">
                        <span>
                            <strong>An error has occurred!</strong> The session could not be invalidated.
                        </span>
                    </Translate>
                </Alert>
            ) : null}

            <Button color="primary" onClick={refreshList}>
                Refresh
            </Button>

            <div className="table-responsive">
                <Table className="table-striped">
                    <thead>
                        <tr>
                            <th>
                                <Translate contentKey="sessions.table.ipaddress">IP Address</Translate>
                            </th>

                            <th>
                                <Translate contentKey="sessions.table.useragent">User agent</Translate>
                            </th>

                            <th>
                                <Translate contentKey="sessions.table.date">Date</Translate>
                            </th>

                            <th />
                        </tr>
                    </thead>

                    <tbody>
                        {sessions.map((s, index) => (
                            <tr key={index}>
                                <td>{s.ipAddress}</td>

                                <td>{s.userAgent}</td>

                                <td>{s.tokenDate}</td>

                                <td>
                                    <Button color="primary" onClick={doSessionInvalidation(s.series)}>
                                        <Translate contentKey="sessions.table.button">Invalidate</Translate>
                                    </Button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </Table>
            </div>
        </div>
    );
};

export default SessionsPage;
