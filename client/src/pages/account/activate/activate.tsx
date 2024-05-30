import {useAppDispatch, useAppSelector} from 'app/config/store';
import React, {useEffect} from 'react';
import {Translate} from 'react-jhipster';
import {Link, useSearchParams} from 'react-router-dom';
import {Alert, Col, Row} from 'reactstrap';

import {activateAction, reset} from './activate.reducer';

const successAlert = (
    <Alert color="success">
        <strong>Your user account has been activated.</strong>

        <Link className="alert-link" to="/login">
            Please sign in.
        </Link>
    </Alert>
);

const failureAlert = (
    <Alert color="danger">
        <Translate contentKey="activate.messages.error">
            <strong>Your user could not be activated.</strong> Please use the registration form to sign up.
        </Translate>
    </Alert>
);

export const ActivatePage = () => {
    const dispatch = useAppDispatch();

    const [searchParams] = useSearchParams();

    useEffect(() => {
        const key = searchParams.get('key');

        dispatch(activateAction(key));
        return () => {
            dispatch(reset());
        };
    }, []);

    const {activationFailure, activationSuccess} = useAppSelector((state) => state.activate);

    return (
        <div>
            <Row className="justify-content-center">
                <Col md="8">
                    <h1>
                        <Translate contentKey="activate.title">Activation</Translate>
                    </h1>

                    {activationSuccess ? successAlert : undefined}

                    {activationFailure ? failureAlert : undefined}
                </Col>
            </Row>
        </div>
    );
};

export default ActivatePage;
